package by.academy.jee.dao;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum RepositoryType {

    MEMORY("memory"),
    POSTGRES("postgres"),
    JPA("jpa"),
    ORM("orm");

    private final String type;
    private static Map<String, RepositoryType> valueToEnum = initValueToEnum();
    private static Map<RepositoryType, String> enumToValue = initEnumToValue();

    RepositoryType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static RepositoryType getTypeByString(String value) {
        return valueToEnum.get(value);
    }

    public static String getStringByType(RepositoryType type) {
        return enumToValue.get(type);
    }

    private static Map<RepositoryType, String> initEnumToValue() {
        Map<RepositoryType, String> map = new EnumMap<RepositoryType, String>(RepositoryType.class);
        for (RepositoryType element : RepositoryType.values()) {
            map.put(element, element.type);
        }
        return Collections.unmodifiableMap(map);
    }

    private static Map<String, RepositoryType> initValueToEnum() {
        RepositoryType[] values = RepositoryType.values();
        Map<String, RepositoryType> map = new HashMap<>(values.length);
        for (RepositoryType element : values) {
            map.put(element.type, element);
        }
        return Collections.unmodifiableMap(map);
    }
}
