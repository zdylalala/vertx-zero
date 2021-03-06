package io.vertx.up.epic;

import io.vertx.core.json.JsonObject;
import io.vertx.zero.exception.heart.JexlExpressionException;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilTc {

    @Test
    public void testAequilatus() {
        final String result = Ut.fromAequilatus(3, 10);
        Assert.assertEquals("0000000003", result);
    }

    @Test
    public void testAequilatus2() {
        final String result = Ut.fromAequilatus(13456, 10);
        Assert.assertEquals("0000013456", result);
    }

    @Test
    public void testAequilatus3() {
        final String result = Ut.fromAequilatus(1567, 4);
        Assert.assertEquals("1567", result);
    }

    @Test
    public void testExpr() {
        final String result = Ut.fromExpression("`${name}`", new JsonObject().put("name", "Lang"));
        System.out.println(result);
    }

    @Test(expected = JexlExpressionException.class)
    public void testExpr1() {
        final String result = Ut.fromExpression("${name}", new JsonObject().put("name", "Lang"));
        System.out.println(result);
    }
}
