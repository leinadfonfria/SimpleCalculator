package es.fonfriapps.simplecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private TextView tvSolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSolution = findViewById(R.id.tv_solution);
        initNumberButtons();
        initOperationButtons();
    }

    private String getTextValue(View view) {
        return ((TextView)view).getText().toString();
    }
    private String getTvSolutionText() {
        return getTextValue(tvSolution);
    }

    private void addContentSolution(String text) {
        tvSolution.setText(getTvSolutionText().concat(text));
    }

    private void setTvContentSolution(String text) {
        tvSolution.setText(text);
    }

    private void resetTvSolution() {
        tvSolution.setText("");
    }

    private void initOperationButtons() {
        findViewById(R.id.bt_plus).setOnClickListener((view -> {
            addOperation(getTextValue(view));
        }));
        findViewById(R.id.bt_minus).setOnClickListener((view -> {
            addOperation(getTextValue(view));
        }));
        findViewById(R.id.bt_eq).setOnClickListener((view -> {
            setTvContentSolution(calculateTotal());
        }));
        findViewById(R.id.bt_ac).setOnClickListener((view) -> {
            resetTvSolution();
        });
    }

    private String calculateTotal() {
        String[] tokens = getTvSolutionText().split(" ");
        int total = 0;
        String previousOp = "";
        for(String token : tokens) {
            if(token.equals(PLUS) || token.equals(MINUS)) {
                previousOp = token;
            } else if(previousOp.isEmpty() || previousOp.equals(PLUS)) {
                total = total + Integer.parseInt(token);
            } else if(previousOp.equals(MINUS)) {
                total = total - Integer.parseInt(token);
            } else {
                return "ERROR";
            }
        }
        return String.valueOf(total);
    }

    private void addOperation(String operation) {
        if(getTvSolutionText().endsWith(" + ")
                || getTvSolutionText().endsWith(" - ")) {
            return;
        }
        addContentSolution(" " + operation + " ");
    }

    private void initNumberButtons() {
        findViewsByIds(
                R.id.bt_0,
                R.id.bt_1,
                R.id.bt_2,
                R.id.bt_3,
                R.id.bt_4,
                R.id.bt_5,
                R.id.bt_6,
                R.id.bt_7,
                R.id.bt_8,
                R.id.bt_9
        ).forEach(view -> setNumberButtonOnClickListener((Button) view));
    }

    private List<View> findViewsByIds(int ... ids) {
        return Arrays.stream(ids).mapToObj(id -> (View)findViewById(id))
                .collect(Collectors.toList());
    }

    private void setNumberButtonOnClickListener(Button btn) {
        btn.setOnClickListener((view -> {
            addContentSolution(getTextValue(view));
        }));
    }
}