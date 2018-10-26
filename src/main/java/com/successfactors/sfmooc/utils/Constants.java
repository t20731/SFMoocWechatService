package com.successfactors.sfmooc.utils;

import java.util.HashSet;
import java.util.Set;

public class Constants {

    public static Set<String> ORDER_FIELD_SET = new HashSet<>();

    static {
        ORDER_FIELD_SET.add(Constants.END_DATE);
        ORDER_FIELD_SET.add(Constants.CREATED_DATE);
        ORDER_FIELD_SET.add(Constants.LAST_MODIFIED_DATE);
        ORDER_FIELD_SET.add(Constants.TOTAL_MEMBERS);
    }

    public static final Integer QUESTION_THRESHOLD = 3;

    public static final String SUCCESS = "ok";

    public static final String ILLEGAL_ARGUMENT = "illegal_argument";

    public static final String NOT_AUTHORIZED = "not_authorized";

    public static final String ERROR = "error";

    public static final String NO_DATA = "no_data";

    public static final String REGISTERED = "registered";

    public static final String END_DATE = "end_date";

    public static final String CREATED_DATE = "created_date";

    public static final String LAST_MODIFIED_DATE = "last_modified_date";

    public static final String TOTAL_MEMBERS = "total_members";

}
