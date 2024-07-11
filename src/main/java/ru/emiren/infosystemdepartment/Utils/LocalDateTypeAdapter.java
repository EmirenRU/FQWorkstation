package ru.emiren.infosystemdepartment.Utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
        jsonWriter.value(localDate != null ? formatter.format(localDate) : null);
    }

    @Override
    public LocalDate read(JsonReader jsonReader) throws IOException {
        return jsonReader != null ? LocalDate.parse(jsonReader.nextString(), formatter) : null;
    }
}
