package com.example.unitconverterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Spinner spinnerUnitType, spinnerSourceUnit, spinnerDestUnit;
    EditText editTextValue;
    TextView textViewConvertedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        spinnerUnitType = findViewById(R.id.spinnerUnitType);
        spinnerSourceUnit = findViewById(R.id.spinnerSourceUnit);
        spinnerDestUnit = findViewById(R.id.spinnerDestUnit);
        editTextValue = findViewById(R.id.editTextValue);
        textViewConvertedValue = findViewById(R.id.textViewConvertedValue);

        // Populate unit type spinner
        ArrayAdapter<CharSequence> unitTypeAdapter = ArrayAdapter.createFromResource(this, R.array.unit_types, android.R.layout.simple_spinner_item);
        unitTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnitType.setAdapter(unitTypeAdapter);

        // Set listener for unit type spinner
        spinnerUnitType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Update unit spinners based on selected unit type
                updateUnitSpinners(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Set click listener for Submit button
        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClicked();
            }
        });
    }

    // Method to update unit spinners based on selected unit type
    private void updateUnitSpinners(int position) {
        int arrayResourceId = 0;
        switch (position) {
            case 0: // Length
                arrayResourceId = R.array.length_units;
                break;
            case 1: // Weight
                arrayResourceId = R.array.weight_units;
                break;
            case 2: // Temperature
                arrayResourceId = R.array.temperature_units;
                break;
        }
        populateSpinnerFromArray(spinnerSourceUnit, arrayResourceId);
        populateSpinnerFromArray(spinnerDestUnit, arrayResourceId);
    }

    // Method to populate a spinner from a string array resource
    private void populateSpinnerFromArray(Spinner spinner, int arrayResourceId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayResourceId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    // onClick method for the Submit button
    private void onSubmitClicked() {
        // Get the input value and selected units
        String inputValue = editTextValue.getText().toString().trim();
        if (inputValue.isEmpty()) {
            Toast.makeText(this, "Please enter a value to convert", Toast.LENGTH_SHORT).show();
            return;
        }

        double value;
        try {
            value = Double.parseDouble(inputValue);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input value", Toast.LENGTH_SHORT).show();
            return;
        }

        String sourceUnit = spinnerSourceUnit.getSelectedItem().toString();
        String destUnit = spinnerDestUnit.getSelectedItem().toString();

        // Convert the value based on the selected unit type
        double convertedValue;
        switch (spinnerUnitType.getSelectedItemPosition()) {
            case 0: // Length
                convertedValue = convertLength(value, sourceUnit, destUnit);
                break;
            case 1: // Weight
                convertedValue = convertWeight(value, sourceUnit, destUnit);
                break;
            case 2: // Temperature
                convertedValue = convertTemperature(value, sourceUnit, destUnit);
                break;
            default:
                convertedValue = 0.0;
        }

        // Display the converted value
        textViewConvertedValue.setText(String.valueOf(convertedValue));
    }

    // Conversion methods
    private double convertLength(double value, String sourceUnit, String destUnit) {
        double result = 0.0;
        switch (sourceUnit) {
            case "Inches":
                result = toInches(value, destUnit);
                break;
            case "Feet":
                result = toFeet(value, destUnit);
                break;
            case "Yards":
                result = toYards(value, destUnit);
                break;
            case "Miles":
                result = toMiles(value, destUnit);
                break;
        }
        return result;
    }

    private double toInches(double value, String destUnit) {
        switch (destUnit) {
            case "Inches":
                return value;
            case "Feet":
                return value * 12.0;
            case "Yards":
                return value * 36.0;
            case "Miles":
                return value * 63360.0;
            default:
                return 0.0;
        }
    }

    private double toFeet(double value, String destUnit) {
        switch (destUnit) {
            case "Inches":
                return value / 12.0;
            case "Feet":
                return value;
            case "Yards":
                return value / 3.0;
            case "Miles":
                return value / 5280.0;
            default:
                return 0.0;
        }
    }

    private double toYards(double value, String destUnit) {
        switch (destUnit) {
            case "Inches":
                return value / 36.0;
            case "Feet":
                return value * 3.0;
            case "Yards":
                return value;
            case "Miles":
                return value / 1760.0;
            default:
                return 0.0;
        }
    }

    private double toMiles(double value, String destUnit) {
        switch (destUnit) {
            case "Inches":
                return value / 63360.0;
            case "Feet":
                return value / 5280.0;
            case "Yards":
                return value / 1760.0;
            case "Miles":
                return value;
            default:
                return 0.0;
        }
    }

    private double convertWeight(double value, String sourceUnit, String destUnit) {
        double result = 0.0;
        switch (sourceUnit) {
            case "Pounds":
                result = toPounds(value, destUnit);
                break;
            case "Ounces":
                result = toOunces(value, destUnit);
                break;
            case "Tons":
                result = toTons(value, destUnit);
                break;
        }
        return result;
    }

    private double toPounds(double value, String destUnit) {
        switch (destUnit) {
            case "Pounds":
                return value;
            case "Ounces":
                return value * 16.0;
            case "Tons":
                return value / 2000.0;
            default:
                return 0.0;
        }
    }

    private double toOunces(double value, String destUnit) {
        switch (destUnit) {
            case "Pounds":
                return value / 16.0;
            case "Ounces":
                return value;
            case "Tons":
                return value / 32000.0;
            default:
                return 0.0;
        }
    }

    private double toTons(double value, String destUnit) {
        switch (destUnit) {
            case "Pounds":
                return value * 2000.0;
            case "Ounces":
                return value * 32000.0;
            case "Tons":
                return value;
            default:
                return 0.0;
        }
    }

    private double convertTemperature(double value, String sourceUnit, String destUnit) {
        double result = 0.0;
        switch (sourceUnit) {
            case "Celsius":
                result = toCelsius(value, destUnit);
                break;
            case "Fahrenheit":
                result = toFahrenheit(value, destUnit);
                break;
            case "Kelvin":
                result = toKelvin(value, destUnit);
                break;
        }
        return result;
    }

    private double toCelsius(double value, String destUnit) {
        switch (destUnit) {
            case "Celsius":
                return value;
            case "Fahrenheit":
                return (value * 9.0 / 5.0) + 32.0;
            case "Kelvin":
                return value + 273.15;
            default:
                return 0.0;
        }
    }

    private double toFahrenheit(double value, String destUnit) {
        switch (destUnit) {
            case "Celsius":
                return (value - 32.0) * 5.0 / 9.0;
            case "Fahrenheit":
                return value;
            case "Kelvin":
                return (value - 32.0) * 5.0 / 9.0 + 273.15;
            default:
                return 0.0;
        }
    }

    private double toKelvin(double value, String destUnit) {
        switch (destUnit) {
            case "Celsius":
                return value - 273.15;
            case "Fahrenheit":
                return (value - 273.15) * 9.0 / 5.0 + 32.0;
            case "Kelvin":
                return value;
            default:
                return 0.0;
        }
    }

}
