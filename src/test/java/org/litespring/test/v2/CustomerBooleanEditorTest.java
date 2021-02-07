package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomBooleanEditor;
import org.litespring.beans.propertyeditors.CustomNumberEditor;

/**
 * @autor sheltersodom
 * @create 2021-02-07-18:20
 */
public class CustomerBooleanEditorTest {
    @Test
    public void testConvertString() {
        CustomBooleanEditor editor = new CustomBooleanEditor(true);

        editor.setAsText("true");
        Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("false");
        Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());

        editor.setAsText("on");
        Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("off");
        Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());

        editor.setAsText("yes");
        Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("no");
        Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());


        try {
            editor.setAsText("aabbcc");
        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail();
    }
}
