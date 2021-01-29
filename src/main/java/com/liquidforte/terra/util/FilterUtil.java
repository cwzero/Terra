package com.liquidforte.terra.util;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

import com.google.common.base.Strings;

public class FilterUtil {
    public static boolean checkFilter(String filter) {
        if (Strings.isNullOrEmpty(filter) || filter.trim().toLowerCase().contentEquals("true")) {
            return true;
        }

        JexlEngine jexl = new JexlBuilder().create();
        JexlExpression filterExpression = jexl.createExpression(filter);
        Boolean value = (Boolean) filterExpression.evaluate(new MapContext());

        if (value != null && value) {
            return true;
        } else {
            return false;
        }
    }
}