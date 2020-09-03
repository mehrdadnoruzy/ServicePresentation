package com.home.servicepresentation.data

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.JsonSyntaxException
import com.home.servicepresentation.data.models.base.BaseModel
import com.home.servicepresentation.data.models.detail.DetailModel
import com.home.servicepresentation.data.models.home.HomeModel
import com.home.servicepresentation.data.network.CheckNetwork
import com.home.servicepresentation.data.network.Network
import org.hamcrest.CoreMatchers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.BufferedInputStream
import java.io.FileNotFoundException
import java.net.MalformedURLException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.home.servicepresentation.data.test", appContext.packageName)
    }
}