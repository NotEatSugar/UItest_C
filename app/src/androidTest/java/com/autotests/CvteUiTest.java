package com.autotests;

import android.app.Instrumentation;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ThreadLocalRandom;

@RunWith(AndroidJUnit4.class)
public class CvteUiTest {
    public Instrumentation mInstrumentation;
    public UiDevice mUiDevice;


    @Before
    public void setup() throws RemoteException {
        //获取对象实例化
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
        mUiDevice = UiDevice.getInstance(mInstrumentation);

        try {
            if (!mUiDevice.isScreenOn()) {
                mUiDevice.wakeUp();//唤醒屏幕
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    //am instrument -w -r -e class 'com.autotests.CvteUiTest#CameraTake' -e additionalTestOutputDir /sdcard/Android/media/com.autotests/additional_test_output -e testTimeoutSeconds 31536000 com.autotests.test/androidx.test.runner.AndroidJUnitRunner
   // am instrument -w -r   -e debug false -e class com.autotests.CvteUiTest#CameraTake  com.autotests.test/androidx.test.runner.AndroidJUnitRunner
    @Test //正式用例必须要注释@Test  用例1
    public void CameraTake() throws RemoteException, UiObjectNotFoundException {
        int x = mUiDevice.getDisplayWidth();
        int y = mUiDevice.getDisplayHeight();
        UiScrollable scroll = new UiScrollable(new UiSelector().className("androidx.viewpager.widget.ViewPager"));
        scroll.setAsHorizontalList();
        UiSelector selector = new UiSelector().text("相机");
        scroll.scrollIntoView(selector);
        mUiDevice.findObject(selector).clickAndWaitForNewWindow(5);
        SystemClock.sleep(3000);
        mUiDevice.click(x / 2, y / 2);
        UiObject shutter_button = new UiObject(new UiSelector().resourceId("com.android.camera2:id/shutter_button"));
        UiObject Video_button = new UiObject(new UiSelector().text("Video"));
        UiObject Picture_button = new UiObject(new UiSelector().text("Picture"));
        //mUiDevice.pressRecentApps(); //打开最近运行任务
        for (int i = 0; i < 2; i++) {
            video_test(Video_button, shutter_button);
            picture_test(Picture_button, shutter_button);
            SystemClock.sleep(2000);
            mUiDevice.findObject(By.res("com.android.camera2:id/btn_camera_switch")).click();
        }

    }

    public void video_test(UiObject Video_button, UiObject shutter_button) throws UiObjectNotFoundException {
        Video_button.click();
        SystemClock.sleep(2000);
        for (int i = 0; i < 11; i++) {
            shutter_button.click();
            if (i % 2 != 0) {
                SystemClock.sleep(2000);
            } else {
                SystemClock.sleep(12000);
            }

        }

    }

    public void picture_test(UiObject Picture_button, UiObject shutter_button) throws UiObjectNotFoundException {
        int x = mUiDevice.getDisplayWidth();
        int y = mUiDevice.getDisplayHeight();
        int randomNum = ThreadLocalRandom.current().nextInt(500, 1300);
        Picture_button.click();

        for (int i = 0; i < 11; i++) {
            mUiDevice.click(x - randomNum, y - randomNum + 300);
            SystemClock.sleep(1000);
            shutter_button.click();
            SystemClock.sleep(2000);
            if (i == 5) {
                mUiDevice.findObject(By.res("com.android.camera2:id/picSizeImg")).click(); //切换拍照比例
            }
        }
    }

    @After
    public void Reset() {
        mUiDevice.pressBack();
        SystemClock.sleep(2000);
    }

}