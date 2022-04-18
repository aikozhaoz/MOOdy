package com.mentalhealthapp.moody;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class JournalEntryTest

{

    @Rule
    public ActivityTestRule<HomeActivity> mActivityHomeRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);


    @Before
    public void setUp() throws Exception {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("JohnSmith@test.com", "test1234");
    }

    @Test
    public void testUserInputScenario(){

        String title = "Test Entry Title";
        String description = "Test Entry Description";

        //input some text in the edit text
        Espresso.onView(withId(R.id.etjournal_title)).perform(typeText(title));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.etjournal_text)).perform(typeText(description));
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.etjournal_title)).check(matches(withText(title)));
        Espresso.onView(withId(R.id.etjournal_text)).check(matches(withText(description)));

        Espresso.onView(withId(R.id.submit_journal)).perform(click());

        Espresso.onView(withId(R.id.review_journals)).perform(click());

       // Espresso.onView(withId(R.id.journalsView)).check(matches(withText(title)));

        onData(anything()).inAdapterView(withId(R.id.journalsView)).atPosition(0).toString().contains(title);
        onData(anything()).inAdapterView(withId(R.id.journalsView)).atPosition(0).toString().contains(description);


//                onData(anything()).inAdapterView(withId(R.id.to_do_list_view)).atPosition(0).
//                onChildView(withId(R.id.item_title)).
//                check(matches(withText("TODO Title Sample")));

        //close soft keyboard
        //checking text in textview
    }



    @After
    public void tearDown() throws Exception {

    }
}
