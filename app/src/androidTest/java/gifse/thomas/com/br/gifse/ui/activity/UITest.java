package gifse.thomas.com.br.gifse.ui.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import gifse.thomas.com.br.gifse.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void gifSearch() {
        ViewInteraction fabSearch = onView(allOf(withId(R.id.fab_search), isDisplayed()));
        fabSearch.perform(click());

        ViewInteraction txvInputError = onView(
                allOf(withId(R.id.textinput_error), withText("Please insert a text to search."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_search),
                                        1),
                                0),
                        isDisplayed()));
        txvInputError.check(matches(withText("Please insert a text to search.")));

        ViewInteraction inputSearch = onView(allOf(withId(R.id.input_search), isDisplayed()));
        inputSearch.perform(click());
        inputSearch.perform(replaceText("doggo"), closeSoftKeyboard());

        fabSearch.perform(click());
        inputSearch.check(matches(withText("doggo")));
    }

    @Test
    public void randomGif() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction linkGetRandom = onView(allOf(withId(R.id.title), withText("Get random"), isDisplayed()));
        linkGetRandom.perform(click());

        ViewInteraction imgRandom1 = onView(
                allOf(withId(R.id.img_random_gif),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                0),
                        isDisplayed()));

        ViewInteraction fabChangeGif = onView(allOf(withId(R.id.fab_update_Random), isDisplayed()));
        fabChangeGif.perform(click());

        ViewInteraction imgRandom2 = onView(
                allOf(withId(R.id.img_random_gif),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        0),
                                0),
                        isDisplayed()));

        ViewInteraction btnRandomBack = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.app_bar_layout)))),
                        isDisplayed()));
        btnRandomBack.perform(click());
    }

    @Test
    public void changeSearch() {
        ViewInteraction inputSearch = onView(allOf(withId(R.id.input_search), isDisplayed()));
        inputSearch.perform(replaceText("cat"), closeSoftKeyboard());

        ViewInteraction fabSearch = onView(allOf(withId(R.id.fab_search), isDisplayed()));
        fabSearch.perform(click());
    }

    @Test
    public void checkStoredSearches() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction linkSearchHistory = onView(allOf(withId(R.id.title), withText("Search history"), isDisplayed()));
        linkSearchHistory.perform(click());

        ViewInteraction txvFirstItem = onView(
                allOf(withId(R.id.history_search_row), withText("cat"),
                        childAtPosition(
                                allOf(withId(R.id.recycler_searches),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                1)),
                                0),
                        isDisplayed()));
        txvFirstItem.check(matches(withText("cat")));

        ViewInteraction recyclerView = onView(allOf(withId(R.id.recycler_searches), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));


        ViewInteraction inputSearch = onView(
                allOf(withId(R.id.input_search), withText("cat"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_search),
                                        0),
                                0),
                        isDisplayed()));
        inputSearch.check(matches(withText("cat")));
    }



    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
