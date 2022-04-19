package com.mentalhealthapp.moody;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> mActivitySignUpRule = new ActivityTestRule<SignUpActivity>(SignUpActivity.class);


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testaUserSignUp(){

        String fname = "John";
        String lname = "Smith";
        String email = "JohnSmith@test.com";
        String password = "test1234";

        //input some text in the edit text
        Espresso.onView(withId(R.id.et_first_name)).perform(typeText(fname));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.et_last_name)).perform(typeText(lname));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.et_email)).perform(typeText(email));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.et_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.et_confirm_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.et_first_name)).check(matches(withText(fname)));
        Espresso.onView(withId(R.id.et_last_name)).check(matches(withText(lname)));
        Espresso.onView(withId(R.id.et_email)).check(matches(withText(email)));
        Espresso.onView(withId(R.id.et_password)).check(matches(withText(password)));
        Espresso.onView(withId(R.id.et_confirm_password)).check(matches(withText(password)));

        Espresso.onView(withId(R.id.signup_button_signup)).perform(click());

   //     Espresso.onView(withId(R.id.submit_journal)).check(matches(isDisplayed()));
    }

    @Test
    public void testbExistingUser(){
        String fname = "John";
        String lname = "Smith";
        String email = "JohnSmith@test.com";
        String password = "test1234";

        //input some text in the edit text
        Espresso.onView(withId(R.id.et_first_name)).perform(typeText(fname));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.et_last_name)).perform(typeText(lname));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.et_email)).perform(typeText(email));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.et_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.et_confirm_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.et_first_name)).check(matches(withText(fname)));
        Espresso.onView(withId(R.id.et_last_name)).check(matches(withText(lname)));
        Espresso.onView(withId(R.id.et_email)).check(matches(withText(email)));
        Espresso.onView(withId(R.id.et_password)).check(matches(withText(password)));
        Espresso.onView(withId(R.id.et_confirm_password)).check(matches(withText(password)));

        Espresso.onView(withId(R.id.signup_button_signup)).perform(click());

        Espresso.onView(withId(R.id.et_first_name)).check(matches(withText(fname)));
        Espresso.onView(withId(R.id.et_last_name)).check(matches(withText(lname)));
        Espresso.onView(withId(R.id.et_email)).check(matches(withText(email)));
        Espresso.onView(withId(R.id.et_password)).check(matches(withText(password)));
        Espresso.onView(withId(R.id.et_confirm_password)).check(matches(withText(password)));

//        String title = "Test entry title";
//        String description = "Test entry description";
//
//        //input some text in the edit text
//        Espresso.onView(withId(R.id.etjournal_title)).perform(typeText(title));
//        Espresso.closeSoftKeyboard();
//
//        Espresso.onView(withId(R.id.etjournal_text)).perform(typeText(description));
//        Espresso.closeSoftKeyboard();
//
//        Espresso.onView(withId(R.id.etjournal_title)).check(matches(withText(title)));
//        Espresso.onView(withId(R.id.etjournal_text)).check(matches(withText(description)));
//
//        Espresso.onView(withId(R.id.submit_journal)).check(matches(isDisplayed()));
    }



    @After
    public void tearDown() throws Exception {

    }
}
