package com.example.youssefiibrahim.musicsheetgenerationapp.Common;

import com.example.youssefiibrahim.musicsheetgenerationapp.Model.Question;
import com.example.youssefiibrahim.musicsheetgenerationapp.Model.User;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static String categoryId, categoryName;
    public static User currentUser;
    public static String currentLink;
    public static List<Question> questionList = new ArrayList<>();
    public static final String STR_PUSH = "pushNotification";
}
