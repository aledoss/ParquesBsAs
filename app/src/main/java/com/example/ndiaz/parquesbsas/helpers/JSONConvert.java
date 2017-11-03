package com.example.ndiaz.parquesbsas.helpers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONConvert {


    private static ObjectMapper mapper = new ObjectMapper();

    private JSONConvert() {}

    public static ObjectMapper getMapper(){
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }


    public static <T> T fromJson(JSONObject json, Class<T> elementType) throws IOException {
        return getMapper().readValue(json.toString(), elementType);

    }

    public static <T> List<T> fromArrayJson(JSONArray objArray, final Class<T> elementType) throws IOException  {
        return getMapper().readValue(objArray.toString(), mapper.constructType(new ArrayListType(elementType)));
    }


    public static <T> String toJsonArray(List<T> items) throws IOException {
        return getMapper().writeValueAsString(items);
    }

    public static <T> String toJsonArray(T item) throws IOException{
        List<T> items= new ArrayList<>();
        items.add(item);
        return toJsonArray(items);
    }

    public static <T> String toJsonArrayWithFilter(List<T> items, String[] fieldsIgnore, String nameFilter) throws IOException {
        FilterProvider filters = new SimpleFilterProvider().addFilter(nameFilter, SimpleBeanPropertyFilter.filterOutAllExcept(fieldsIgnore));
        getMapper().setFilterProvider(filters);

        return getMapper().writeValueAsString(items);
    }

    //Type para obtener un ArrayList de la clase parametrizada
    private static class ArrayListType implements ParameterizedType {

        private Type type;

        public ArrayListType(Type type) {
            this.type = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[] {type};
        }

        @Override
        public Type getRawType() {
            return ArrayList.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
