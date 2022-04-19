package com.mentalhealthapp.moody;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LogInTest

{

    @Rule
    public ActivityTestRule<MainActivity> mActivityMainRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Before
    public void setUp() throws Exception {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null){
            auth.signOut();
        }
    }

    @Test
    public void testLogIn(){

        String email = "JohnSmith@test.com";
        String password = "test1234";

        //input some text in the edit text
        Espresso.onView(withId(R.id.main_button_logIn)).perform(click());

        Espresso.onView(withId(R.id.et_email)).perform(typeText(email));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.et_password)).perform(typeText(password));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.et_email)).check(matches(withText(email)));
        Espresso.onView(withId(R.id.et_password)).check(matches(withText(password)));

        Espresso.onView(withId(R.id.login_button_login)).perform(click());

        Espresso.onView(withId(R.id.submit_journal)).check(matches(isDisplayed()));

    }



    @After
    public void tearDown() throws Exception {

    }
}