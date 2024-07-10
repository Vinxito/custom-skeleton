package com.mock.shared.infrastructure.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.mock.shared.domain.criteria.Criteria;
import com.mock.shared.domain.criteria.FilterOperator;
import com.mock.shared.domain.criteria.FilterValue;
import com.mock.shared.domain.criteria.Filters;
import com.mock.shared.domain.errors.DomainError;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CriteriaDeserializer {

    private final ObjectMapper mapper;

    public CriteriaDeserializer() {
        SimpleModule filterValueModule = new SimpleModule();
        filterValueModule.addDeserializer(FilterValue.class,
                                          new FilterValueDeserializer());

        SimpleModule filterOperatorModule = new SimpleModule();
        filterOperatorModule.addDeserializer(FilterOperator.class,
                                             new FilterOperatorDeserializer());

        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(ZonedDateTimeSerializer.INSTANCE);
        module.addDeserializer(LocalDateTime.class,
                               LocalDateTimeDeserializer.INSTANCE);
        mapper = new ObjectMapper()
                .setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy())
                .setVisibility(PropertyAccessor.ALL,
                               JsonAutoDetect.Visibility.ANY)
                .registerModule(module)
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
                .registerModule(filterValueModule)
                .registerModule(filterOperatorModule);
    }

    public HashMap<String, Serializable> exec(String query) {

        try {
            Criteria criteriaParsed = !query.isEmpty() ?
                                      mapper.readValue(query,
                                                       Criteria.class) :
                                      new Criteria(Filters.empty());
            return criteriaParsed.toPrimitives();
        } catch (JsonProcessingException exception) {
            throw new CriteriaDeserializerError(query);
        }
    }

    public static class FilterValueDeserializer extends StdDeserializer<FilterValue> {
        public FilterValueDeserializer() {
            this(null);
        }

        public FilterValueDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public FilterValue deserialize(JsonParser jp,
                                       DeserializationContext ctxt)
                throws IOException {
            JsonNode valueNode = jp.getCodec().readTree(jp);

            if (valueNode.isTextual()) {
                return new FilterValue(valueNode.asText());
            } else if (valueNode.isBoolean()) {
                return new FilterValue(valueNode.asBoolean());
            } else if (valueNode.isNumber()) {
                return new FilterValue(valueNode.asDouble());
            } else if (valueNode.isArray()) {
                List<Serializable> arrayValues = new ArrayList<>();
                for (JsonNode arrayElement : valueNode) {
                    if (arrayElement.isTextual()) {
                        arrayValues.add(arrayElement.asText());
                    } else if (arrayElement.isNumber()) {
                        arrayValues.add(arrayElement.asDouble());
                    }
                }
                return new FilterValue(arrayValues);
            }
            throw new IllegalArgumentException("Unsupported value type");
        }
    }

    public static class FilterOperatorDeserializer extends StdDeserializer<FilterOperator> {
        public FilterOperatorDeserializer() {
            this(null);
        }

        public FilterOperatorDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public FilterOperator deserialize(JsonParser jp,
                                          DeserializationContext ctxt)
                throws IOException {
            JsonNode valueNode = jp.getCodec().readTree(jp);

            if (valueNode.isTextual()) {
                return FilterOperator.fromValue(valueNode.asText());
            }
            throw new IllegalArgumentException("Unsupported value type");
        }
    }

    public final class CriteriaDeserializerError extends DomainError {
        public CriteriaDeserializerError(String query) {
            super("CriteriaDeserializerError",
                  String.format("Error deserializing the criteria on a controller with query: %s",
                                query));
        }
    }

}
