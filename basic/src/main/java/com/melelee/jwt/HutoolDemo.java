package com.melelee.jwt;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * @author menglili
 */
public class HutoolDemo {


    public static final String KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvoQAd2GSOAdCkvi9XpRUdJnmvvatjipf2MceqIkc1X15qYPKa/hwPjPJx9P3vMvMN2FsszEyjje5uK3KFb0KzndP8E4HW2nUN0jkUmMkeV4hURH0xAwydFMpgtI8X4yHsZho5+3InYxm8PnEcYE+x69WMiTAbOsRsZy9AxMTUEUxP0HUnmTELf3ihMi1lfcZ+z7VTjvGrdIy9MyP//ntsxbv0tqd/xtliJuTsHxRlJYosDDB+yzyuO36XLEtqw83UcPYzYSVM3phlmlLpTh+bTIMdA0frrfsedb58Yzrkii/5f/l4ZIrlNkeGaCEaSF0pb6O1rCJ3R8nt4idChnjmQIDAQAB";
    public static final String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySW5mbyI6eyJ1c2VySWQiOjcsInVzZXJuYW1lIjoieHhzIiwicmVhbG5hbWUiOiLlvpDopb_mgJ0iLCJwaG9uZSI6IjE4MDEyMzQ1Njc4IiwicGFzc3dvcmQiOiJmODlhODNjYjE2YTFjZjg0Iiwic2FsdCI6IjYzMjkzMTg4Iiwic3RhdHVzIjoxLCJjb21wYW55SWQiOjIsImNvbXBhbnlOYW1lIjoi5oub5qCH5Lq65YWs5Y-4IiwibWFpblVpZENvZGUiOiI5MTM0MDEwMDc1NDg3NzU3MDMiLCJjb21wYW55VHlwZSI6MiwiZGF0YVBlcm1pc3Npb25zIjpbeyJkYXRhUGVybWlzc2lvbkNvZGUiOjIwMDIsImFsbCI6dHJ1ZSwic2VsZiI6dHJ1ZSwiZGVwdElkcyI6Wzc2XX1dfSwidXNlcl9uYW1lIjoieHhzIiwic2NvcGUiOlsiYWxsIl0sImV4cCI6MTY4ODEyOTMzNiwiYXV0aG9yaXRpZXMiOlsidGVzdCIsInVzZXI6YWRkIl0sImp0aSI6ImEyYTAxMTc1LTdkYjItNGU4ZC05NTQzLTEwZmQ1NTMwYzExMSIsImNsaWVudF9pZCI6InRlbmRlci1nYXRld2F5In0.GYAc-wkF6Mhnt17W4RstfLJznfQh4jqx-QhyRVggu8sspUwoRz1J9ZuGW67SRPFbKu3cjBmBBF4m2Q2zxKDtnjxFkFHz4xRWw0T5u_vKVS-_DNIvp49zlph0bR9nvYo6GPEurxUV1oRQuYaHQq7VybN9w4MtuAMabJE9dXJsbSI7qipsf315rBmRoi7BywUP9yDmQ8rtJVcxcXl2MdY3nsm19A_-Yom0YSarOvH3ub8klohBTna2GQvFShg79a2UnwNgvBIvu-uXHwaMVMKrQmIvwN9KVtQXH1mKP0xo_21EDvefSKJRzbD1yeRFY2FvxcGKMaq3PwA1NM3j7hHZJQ";


    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException, SignatureException {

//
//        JWT jwt = JWTUtil.parseToken(TOKEN);
//        if (!jwt.setKey(KEY.getBytes(StandardCharsets.UTF_8)).verify()) {
//            throw new RuntimeException("token 异常");
//        }
//
//        if (!jwt.validate(0)) {
//            throw new RuntimeException("token 已过期请重新登录");
//        }
//        System.out.println(jwt.getPayloads());


        KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = rsa.generateKeyPair();
        System.out.println(keyPair.getPrivate().getAlgorithm());
        String privateKey = Base64.encode(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.encode(keyPair.getPublic().getEncoded());

        System.out.println(publicKey);
        System.out.println(privateKey);


        Sign signObj = SecureUtil.sign(SignAlgorithm.SHA256withRSA, privateKey, null);

        byte[] signBytes = signObj.sign("sdf");

        System.out.println(Base64.encode(signBytes));

    }


    public static final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtg4ar13u3w5iocYf3YXs58/yATyzibPNmDxgfAS3XxOPDTV4JNlZsf7Wyys+oHMYMkfUcviehGzIrmNd/+INCdpY/xQ6gwA1+CbkeH/cxx2iKVTKGh4d1XDVF0I6TSej4PzxNGOm6fZ+cJDGoO6edsDwqsEBrhmc2xfv52pDgbE1AcZK18C3OWD5ZuRcJckul5vq2bcwMxlvBEavlwhwLjG0gIa3Xg9Ys2L0QzBQaFiT9WAaOCb+CV0j56LnNhBcNw6AgmpIYVIhLQdSzMlnZk/i/TDQTAorxC6tfqu+MBj6j/tl8ZEqVU97vdY+zplVX4UpiZTfUyeqlIr8L7PpvQIDAQAB";
    public static final String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC2DhqvXe7fDmKhxh/dheznz/IBPLOJs82YPGB8BLdfE48NNXgk2Vmx/tbLKz6gcxgyR9Ry+J6EbMiuY13/4g0J2lj/FDqDADX4JuR4f9zHHaIpVMoaHh3VcNUXQjpNJ6Pg/PE0Y6bp9n5wkMag7p52wPCqwQGuGZzbF+/nakOBsTUBxkrXwLc5YPlm5FwlyS6Xm+rZtzAzGW8ERq+XCHAuMbSAhrdeD1izYvRDMFBoWJP1YBo4Jv4JXSPnouc2EFw3DoCCakhhUiEtB1LMyWdmT+L9MNBMCivELq1+q74wGPqP+2XxkSpVT3u91j7OmVVfhSmJlN9TJ6qUivwvs+m9AgMBAAECggEAHj4zkqRRFT7nhaZLDtq7ozq5Ihl6Xcq/waVuBcwmxiWSowgZ4E03CEmkVvXlHnRgNaIXGc5RTwBfShD5wZNExTk/hYhPvAlh7cpQM/BRtcYndSQ/upz8KcPnskhvNPf70llSnHhddwpasMpsJLnfGy0sDcGoTMq2cvMECHZvpnCInf+wKrLmj60E6eU3y62mAmWCGym67K1D/BSDDBm8yNCYcysQEXO8Kql9fKXlfittGpeIVGrZmLNslGwrNnqX7FcvLcif5IpHsVmCKMginTEmojg5lIdFNS+IMlSrH/NvscaX8rkpxoEi9zM6TU6DeFaI7f292LhKW54S4kT5YQKBgQDusqZTDStOOiIoouKk8Bh/m9ORtztULKzW4LfmniQ1VVkzXbjBlgMyGS2LLYUmpVnIU8XqmiVw7cziHYbxpNoGZUB8JF/t7IlVMmGrX/0I40Enhr2OFlrpGdMRO5x1xgie+kDhLDcqSbyHp3ntmXRtJjeDJU0NzivcLQTXVMwp1QKBgQDDQF/OKXZRchlX53yVxlz03e5J1aqgmE1mF6XZfQX5vxy2kOLn31TLncRxwhb/Dua74FLMaWB6YJnYHrItU/BG165IuV/h5QOTt9aZSTzgL35Jwy4/uRYAIgEwYbO1IToanCQmutaLKdzM/6ovSZUZWUJVU1ulEQ4TNZfQnm4MSQKBgDcGsBkNPCJ4fE9p3V2wi7U/MvKwjtjEw8N1Ym+jnS0+U6XnwSO/dVUjQMCI6d0S2TO5PDLs/hyedVDbBbTHEAvRY6A6snKiyLX/O0zKgAaAfRCjmycaODLsZR/f4h20XWBoyHQgQUdaYD/Vd+IyMgjHepydCLpaAPps1mTdg4NRAoGAdg5UX0k+mxxMKpRzBfR8fCo8TDR/CL9N/S7LPueTV2CwscqFCAJHOR5Zixb55JpT9ME8DxCgHx6NptlYjoO0QoFiheJgESvxr712ahSYVE0jJOJ48lqs7MOiyGgqypgek7xZN3m9A0P6l5D2i8UH+922d/UA8puPURoUgzVoANECgYEAp8n5b2a3KrQXSLUcY3KsCVIXf0GX66xd4VzW/z5EQ2hj1XCVTP1fL6OVD9RL8cibXXsJwphK3qTb5rPBy6Sz9IylBOhbDPth/eay/AG1CXSsi/bizS2rhWnjfXfR7/GdTPsQZLTiXxEMOYoqkTmUZxIIDZqRevVUw+jS4zVBqaw=";

}

