package com.vodka.sotodo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class pomodaraPrompt extends DialogFragment {
    private String name = null;
    private String date = null;
    private int seq = 0;
    private Spinner events;
    private String selectedEvent;
    private boolean finish;
    private Handler mHandler;
    private eventsRepo repo;

    public interface confirmListener {
        void onConfirmComplete(boolean complete, String event);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder message = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_pomodora_prompt, null);
        message.setView(view);

        events = (Spinner)view.findViewById(R.id.event);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.event, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        events.setAdapter(arrayAdapter);
        events.setSelection(0);

        repo = new eventsRepo(getActivity());

        TextView text = (TextView) view.findViewById(R.id.text);

        events.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedEvent = (String) events.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedEvent = (String)events.getSelectedItem();
            }
        });

        Bundle bundle = getArguments();
        name = bundle.getString("name");
        date = bundle.getString("date");
        seq = bundle.getInt("seq");

        message.setTitle("提示");
        if(seq == 1 || seq == 3 || seq == 5) {
            events.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
            message.setMessage("你已经完成第" + (seq + 1) / 2 + "个番茄时钟，休息一会吧！");
        }
        else if(seq == 7) {
            message.setMessage("你已经工作一小时了，此次番茄钟即将结束，放松一下吧！");
            events.setVisibility(View.INVISIBLE);
            text.setVisibility(View.INVISIBLE);
        }
        else if(seq == 2 || seq == 4 || seq == 6) {
            message.setMessage("休息结束，继续工作吧！");
            events.setVisibility(View.INVISIBLE);
            text.setVisibility(View.INVISIBLE);
        }
        message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmListener listener = (confirmListener) getActivity();
                listener.onConfirmComplete(true, selectedEvent);
                if(seq == 1 || seq == 3 || seq == 5 || seq == 7) {
                    Events e = new Events(name, date, selectedEvent, 25);
                    repo.insert(e);
                }
                //endDialog(true);
            }
        });

        return message.create();
    }

    public boolean getFinish() {
        return finish;
    }

    private void setFinish(boolean ifFinished) {
        this.finish = ifFinished;
    }

    public void endDialog(boolean ifFinished) {
        dismiss();
        setFinish(ifFinished);
        Message m = mHandler.obtainMessage();
        mHandler.sendMessage(m);
    }

    public boolean showDialog() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                throw new RuntimeException();
            }
        });
        super.show(getFragmentManager(), null);
        try {
            Looper.getMainLooper().loop();
        } catch (RuntimeException e2) {

        }
        return finish;
    }

}
