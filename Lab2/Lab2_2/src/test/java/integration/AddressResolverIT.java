package integration;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolver;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressResolverIT {


    private AddressResolver resolver;
    private ISimpleHttpClient httpClient;
    @BeforeEach
    public void init(){
        httpClient = new TqsBasicHttpClient();
        resolver  = new AddressResolver(httpClient);
    }

    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {

        //todo

        // repeat the same tests conditions from AddressResolverTest, without mocks
        Optional<Address> result = resolver.findAddressForLocation(40.633116, -8.658784);
        Optional<Address> expected = Optional.of(new Address( "Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null));
        assertEquals( expected, result);


    }

    @Test
    public void whenBadCoordidates_trhowBadArrayindex() throws IOException, URISyntaxException, ParseException {
        assertThrows(IndexOutOfBoundsException.class,
                () -> resolver.findAddressForLocation(-361,-361));

    }

    @Test
    public void  whenBadURI_throwNull() throws  IOException, URISyntaxException, ParseException {
        assertThrows(NullPointerException.class,
                () -> httpClient.doHttpGet(null));
    }

}
