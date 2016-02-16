package api.security;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultAuthTokenServiceTest {
    private final String testUsername = "testUser";
    private DefaultAuthTokenService authTokenService;
    private UserDetails mockedUserDetails;

    @Before
    public void init() {
        authTokenService = new DefaultAuthTokenService();
        authTokenService.expirationMinutes = 10;
        mockedUserDetails = mock(UserDetails.class);
    }

    @Test
    public void registerUserToken() throws Exception {
        initMockedUserDetailsService();

        AuthToken authToken = authTokenService.registerUserToken(testUsername);

        assertEquals(mockedUserDetails, authTokenService.getUserDetailsByToken(authToken));
    }

    @Test
    public void unregisterToken() throws Exception {
        initMockedUserDetailsService();

        AuthToken authToken = authTokenService.registerUserToken(testUsername);
        authTokenService.unregisterToken(authToken);

        assertNull(authTokenService.getUserDetailsByToken(authToken));
    }

    private void initMockedUserDetailsService() {
        UserDetailsService mockedUserDetailsService = mock(UserDetailsService.class);
        when(mockedUserDetailsService.loadUserByUsername(testUsername)).thenReturn(mockedUserDetails);
        authTokenService.userDetailsService = mockedUserDetailsService;
    }
}