package com.jeff.lombok;

import com.jeff.model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestGetSet {

    @Test
    public void test() {
        User user = mock(User.class);
        when(user.getName()).thenReturn("Jeff");
        assertEquals("Jeff", user.getName());
    }
}
