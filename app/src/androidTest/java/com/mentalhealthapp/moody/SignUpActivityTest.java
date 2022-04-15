package com.mentalhealthapp.moody;


import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> mActivitySignUpRule = new ActivityTestRule<SignUpActivity>(SignUpActivity.class);

    private String fName = "Thomas";


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testUserInputScenario(){
        //input some text in the edit text
        Espresso.onView(withId(R.id.et_first_name)).perform(typeText(fName));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.et_first_name)).check(matches(withText(fName)));
        //close soft keyboard
        //checking text in textview
    }



    @After
    public void tearDown() throws Exception {

    }
}
