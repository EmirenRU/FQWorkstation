package ru.emiren.infosystemdepartment.Service.Deserialization;

import java.io.InputStream;
import java.util.List;

public interface DeserializationService {
    List<List<String>> deserializeFile(InputStream inputStream);
}
