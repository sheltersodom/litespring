package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomNumberEditor;

/**
 * @autor sheltersodom
 * @create 2021-02-07-18:20
 */
public class CustomerNumberEditorTest {
    @Test
    public void testConvertString() {
        CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);
        editor.setAsText("3");
        Object value = editor.getValue();
        Assert.assertTrue(value instanceof Integer);
        Assert.assertEquals(3, ((Integer) editor.getValue()).intValue());

        editor.setAsText("");
        Assert.assertTrue(editor.getValue() == null);


        try {
            editor.setAsText("3.1");
        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail();
    }
}
