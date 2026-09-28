package com.samp.mobile.launcher.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.joom.paranoid.Obfuscate;
import com.samp.mobile.R;
import com.samp.mobile.launcher.MainActivity;
import com.samp.mobile.launcher.util.SharedPreferenceCore;

import org.ini4j.Wini;

import java.io.File;

@Obfuscate
public class SettingsFragment extends Fragment {

    private Wini mWini = null;
    private EditText mNickName;
    private SwitchCompat mKeyboardSwitch;
    private SwitchCompat mVoiceSwitch;
    private SwitchCompat mModifySwitch;
    private SwitchCompat mFPSSwitch;
    private SwitchCompat mMonetSwitch;
    private SeekBar mMessagesSeekBar;
    private TextView mMessagesText;
    private SeekBar mFPSSeekBar;
    private TextView mFPSText;

    @Nullable
    private File getIniFile() {
        Context context = getContext();
        if (context == null) return null;
        File extDir = context.getExternalFilesDir(null);
        if (extDir == null) return null;
        File sampDir = new File(extDir, "SAMP");
        if (!sampDir.exists()) {
            sampDir.mkdirs();
        }
        return new File(sampDir, "settings.ini");
    }

    private void saveIniValue(String section, String key, Object value) {
        try {
            File iniFile = getIniFile();
            if (iniFile == null) return;
            if (mWini == null) {
                if (!iniFile.exists()) {
                    iniFile.createNewFile();
                }
                mWini = new Wini(iniFile);
            }
            mWini.put(section, key, value);
            mWini.store();
        } catch (Exception e) {
            Log.e("SettingsFragment", "Error saving INI setting [" + section + "] " + key + "=" + value, e);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_settings, viewGroup, false);

        try {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).hideKeyboard(getActivity());
            }
        } catch (Exception e) {
            Log.e("SettingsFragment", "Error hiding keyboard", e);
        }

        mNickName = view.findViewById(R.id.settings_nickname);
        mKeyboardSwitch = view.findViewById(R.id.keyboard_switch);
        mFPSSwitch = view.findViewById(R.id.fps_switch);
        mMonetSwitch = view.findViewById(R.id.monet_switch);
        mVoiceSwitch = view.findViewById(R.id.voice_switch);
        mModifySwitch = view.findViewById(R.id.modify_switch);
        mMessagesSeekBar = view.findViewById(R.id.messages_seekbar);
        mMessagesText = view.findViewById(R.id.messages_count);
        mFPSSeekBar = view.findViewById(R.id.fps_seekbar);
        mFPSText = view.findViewById(R.id.fps_count);

        File file = getIniFile();
        if (file != null) {
            try {
                if (file.exists()) {
                    mWini = new Wini(file);
                    String nick = mWini.get("client", "name");
                    if (nick != null && mNickName != null) {
                        mNickName.setText(nick);
                    }
                }
            } catch (Exception e) {
                Log.e("SettingsFragment", "Error reading INI file", e);
            }
        }

        if (mNickName != null) {
            mNickName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence != null) {
                        saveIniValue("client", "name", charSequence.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            });
        }

        if (mModifySwitch != null) {
            mModifySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Context context = getContext();
                    if (context != null) {
                        new SharedPreferenceCore().setBoolean(context.getApplicationContext(), "MODIFIED_DATA", b);
                    }
                }
            });
        }

        if (mKeyboardSwitch != null) {
            mKeyboardSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Context context = getContext();
                    if (context != null) {
                        new SharedPreferenceCore().setBoolean(context.getApplicationContext(), "ANDROID_KEYBOARD", b);
                    }
                    saveIniValue("gui", "androidkeyboard", b);
                }
            });
        }

        if (mVoiceSwitch != null) {
            mVoiceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Context context = getContext();
                    if (context != null) {
                        new SharedPreferenceCore().setBoolean(context.getApplicationContext(), "VOICE_CHAT", b);
                    }
                    saveIniValue("gui", "VoiceChatEnable", b);
                }
            });
        }

        if (mFPSSwitch != null) {
            mFPSSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Context context = getContext();
                    if (context != null) {
                        new SharedPreferenceCore().setBoolean(context.getApplicationContext(), "FPS_DISPLAY", b);
                    }
                    saveIniValue("gui", "fps", b ? 1 : 0);
                }
            });
        }

        if (mMonetSwitch != null) {
            mMonetSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Context context = getContext();
                    if (context != null) {
                        new SharedPreferenceCore().setBoolean(context.getApplicationContext(), "MLOADER", b);
                    }
                }
            });
        }

        if (mMessagesSeekBar != null) {
            mMessagesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int realProgress = 6;
                    switch(progress) {
                        case 0: realProgress = 6; break;
                        case 1: realProgress = 9; break;
                        case 2: realProgress = 12; break;
                        case 3: realProgress = 15; break;
                    }
                    Context context = getContext();
                    if (context != null) {
                        new SharedPreferenceCore().setInt(context.getApplicationContext(), "MESSAGE_COUNT", realProgress);
                    }
                    saveIniValue("gui", "ChatMaxMessages", realProgress);
                    if (mMessagesText != null) {
                        mMessagesText.setText(String.valueOf(realProgress));
                    }
                }

                public void onStartTrackingTouch(SeekBar seekBar) {}
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }

        if (mFPSSeekBar != null) {
            mFPSSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int realProgress = 30;
                    switch(progress) {
                        case 0: realProgress = 30; break;
                        case 1: realProgress = 60; break;
                        case 2: realProgress = 90; break;
                        case 3: realProgress = 120; break;
                    }
                    Context context = getContext();
                    if (context != null) {
                        new SharedPreferenceCore().setInt(context.getApplicationContext(), "FPS_LIMIT", realProgress);
                    }
                    saveIniValue("gui", "FPSLimit", realProgress);
                    if (mFPSText != null) {
                        mFPSText.setText(String.valueOf(realProgress));
                    }
                }

                public void onStartTrackingTouch(SeekBar seekBar) {}
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Context context = getContext();
        if (context == null) return;

        try {
            if (mKeyboardSwitch != null) {
                mKeyboardSwitch.setChecked(new SharedPreferenceCore().getBoolean(context.getApplicationContext(), "ANDROID_KEYBOARD"));
            }
            if (mVoiceSwitch != null) {
                mVoiceSwitch.setChecked(new SharedPreferenceCore().getBoolean(context.getApplicationContext(), "VOICE_CHAT"));
            }
            if (mFPSSwitch != null) {
                mFPSSwitch.setChecked(new SharedPreferenceCore().getBoolean(context.getApplicationContext(), "FPS_DISPLAY"));
            }
            if (mModifySwitch != null) {
                mModifySwitch.setChecked(new SharedPreferenceCore().getBoolean(context.getApplicationContext(), "MODIFIED_DATA"));
            }
            if (mMonetSwitch != null) {
                mMonetSwitch.setChecked(new SharedPreferenceCore().getBoolean(context.getApplicationContext(), "MLOADER"));
            }

            int fps = new SharedPreferenceCore().getInt(context, "FPS_LIMIT");
            if (mFPSSeekBar != null) {
                switch (fps) {
                    case 30: mFPSSeekBar.setProgress(0); break;
                    case 60: mFPSSeekBar.setProgress(1); break;
                    case 90: mFPSSeekBar.setProgress(2); break;
                    case 120: mFPSSeekBar.setProgress(3); break;
                }
            }
            if (mFPSText != null) {
                mFPSText.setText(String.valueOf(fps));
            }

            int message = new SharedPreferenceCore().getInt(context, "MESSAGE_COUNT");
            if (mMessagesSeekBar != null) {
                switch (message) {
                    case 6: mMessagesSeekBar.setProgress(0); break;
                    case 9: mMessagesSeekBar.setProgress(1); break;
                    case 12: mMessagesSeekBar.setProgress(2); break;
                    case 15: mMessagesSeekBar.setProgress(3); break;
                }
            }
            if (mMessagesText != null) {
                mMessagesText.setText(String.valueOf(message));
            }
        } catch (Exception e) {
            Log.e("SettingsFragment", "Error updating settings UI in onResume", e);
        }
    }
}
