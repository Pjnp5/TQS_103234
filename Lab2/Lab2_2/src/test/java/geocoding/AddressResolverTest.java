package geocoding;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @Mock
    ISimpleHttpClient httpClient;
    @InjectMocks
    AddressResolver resolver;
    @Test
    @Disabled
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {

        //todo: implement test; remove Disabled annotation
        //will crash for now...need to set the resolver before using it
        String response = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"© 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.633116,\"lng\":-8.658784}},\"locations\":[{\"street\":\"Avenida João Jacinto de Magalhães\",\"adminArea6\":\"Aveiro\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Aveiro\",\"adminArea5Type\":\"City\",\"adminArea4\":\"Aveiro\",\"adminArea4Type\":\"County\",\"adminArea3\":\"\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3810-149\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"L\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.63312,\"lng\":-8.65873},\"displayLatLng\":{\"lat\":40.63312,\"lng\":-8.65873},\"mapUrl\":\"\"}]}]}";

        lenient().when( httpClient.doHttpGet(contains("location=40.633116%2C-8.658784"))).thenReturn(
                response
        );

        Optional<Address> result = resolver.findAddressForLocation(40.633116,-8.658784);

        System.out.println(result);
        //return
        Address expected = new Address( "Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());

    }

    @Test
    @Disabled
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        when( httpClient.doHttpGet(contains("location=-361.000000%2C-361.000000"))).thenThrow(
                IndexOutOfBoundsException.class
        );


        Optional<Address> result = resolver.findAddressForLocation( -361,-361);
        // verify no valid result
        assertFalse(result.isPresent());

    }

    @Test
    @Disabled
    public  void whenBadURI_throwNull() throws IOException, URISyntaxException, ParseException{
        when( httpClient.doHttpGet(isNull())).thenThrow(
                NullPointerException.class
        );
        assertThrows(NullPointerException.class,
                ()-> httpClient.doHttpGet(null));

    }
}