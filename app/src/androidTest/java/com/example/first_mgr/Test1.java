package com.example.first_mgr;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.is;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test1 {
    private void openDrawer() {
        DrawerLayout drawerLayout = activityRule.getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Rule
    public ActivityTestRule<menu_activity> activityRule = new ActivityTestRule<>(menu_activity.class);

        @Test
    public void Test_trening() {
        openDrawer();
        onView(withId(R.id.nav_trening_menu)).perform(click());
        onView(withText("Rozciąganie")).perform(click());
        onView(withText("Rozciąganie")).check(matches(isDisplayed()));
    }
    @Test
    public void Test_nba() {
        openDrawer();
        onView(withId(R.id.nav_newsy))
                .perform(click());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.spinnerTeam)).check(matches(isDisplayed()));
        onView(withId(R.id.spinnerSeason)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerViewGames))
                .check(matches(isDisplayed()))
                .check((view, noViewFoundException) -> {
                    if (noViewFoundException != null) {
                        throw noViewFoundException;
                    }
                    RecyclerView recyclerView = (RecyclerView) view;
                    RecyclerView.Adapter adapter = recyclerView.getAdapter();
                    if (adapter == null) {
                        throw new AssertionError("Adapter is null");
                    }
                    int itemCount = adapter.getItemCount();
                    assertThat(itemCount, is(82));
                });}
    @Test
    public void Test_notatnik() {
        openDrawer();
        onView(withId(R.id.nav_notatnik)).check(matches(isDisplayed()));
    }

    @Test
    public void Test_gry() {
        openDrawer();
        onView(withId(R.id.nav_gry)).check(matches(isDisplayed()));
    }

    @Test
    public void Test_tablica() {
        openDrawer();
        onView(withId(R.id.nav_drawing)).perform(click());
        onView(withId(R.id.btn_save)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_clear)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_saved)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_change_background)).check(matches(isDisplayed()));
        onView(withId(R.id.drawing_view)).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isEnabled();}
            @Override
            public String getDescription() {
                return "Drawing on view";
            }
            @Override
            public void perform(UiController uiController, View view) {
                float centerX = view.getWidth() / 2.0f;
                float centerY = view.getHeight() / 2.0f;
                MotionEvent downEvent = MotionEvent.obtain(
                        android.os.SystemClock.uptimeMillis(),
                        android.os.SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN, centerX, centerY, 0);
                view.dispatchTouchEvent(downEvent);}});
        onView(withId(R.id.drawing_view)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                DrawingView drawingView = (DrawingView) view;
                android.graphics.Path path = drawingView.getPath();
                assertThat(path.isEmpty(), is(false));}});
        onView(withId(R.id.btn_clear)).perform(click());
        onView(withId(R.id.drawing_view)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                DrawingView drawingView = (DrawingView) view;
                android.graphics.Path path = drawingView.getPath();
                assertThat(path.isEmpty(), is(true));}});
        onView(withId(R.id.btn_change_background)).perform(click());
        onView(withId(R.id.drawing_view)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                DrawingView drawingView = (DrawingView) view;
                Bitmap backgroundBitmap = drawingView.getBackgroundBitmap();
                drawingView.changeBackground();
                Bitmap newBackground = drawingView.getBackgroundBitmap();
                assertThat(newBackground.sameAs(backgroundBitmap), is(false));}});
        onView(withId(R.id.btn_save)).perform(click());
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject editText = device.findObject(new UiSelector().resourceId("android:id/input"));
        try {
            if (editText.exists() && editText.isEnabled()) {
                editText.setText("Nazwa rysunku");
                device.pressEnter();}
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();}
        onView(withText("Zapisz")).perform(click());
        onView(withId(R.id.btn_saved)).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));}}