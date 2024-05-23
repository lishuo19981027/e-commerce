package com.imooc.ecommerce.constant;



    /*
    * 授权需要的一些常量信息
    * */
public class AuthorityConstant {

    //RSA私钥，除了授权中心之外不暴露
    public static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCynNkux0S" +
            "4k2uWfWB081lDX2obgebbaEZ2XdKqN6oQ/1gP15aDBy/ba8CSR8w9P/3WB2/26wfWIH3keCpm8LyqLc6qkjfGS3D5+DU" +
            "4vqwzfRBf8eBdWLS3QWaWNEfBMn+Ue/VIr6KVkuIg368O7uaeVX6DN83gTYkFN7W/54KDiUYoGAGJhmzLRlj+D5xpsqyA4" +
            "bDjwQ6pEn5rXcQdkAyyTi2d8mJyaL4pUoRXNVugHDbzhlKCApd6op0f0LM2/Oizo2UIYQ6y7NmzgfVLOKLyPzpS4AeRy368P" +
            "U2yK08b9ZWLsloykGJ2xuyjQramX55rkgH9SvEr4E997jdLPBD5AgMBAAECggEAelYkC35A+4s9rZ3Ca1giU5sOujiHPtB1Mq" +
            "2glSX7Phkm3/LyDFzn0opDI+45XdpuqiH99kmU3Hz0vX/wywEouAcX/oyX1yHlB9GR4oP2vm1ywcSRwF35qWsKMOcuHo3QBIvCv" +
            "T5PRdONPqb26GuJWfmE5atbP77bks9NlVukcSJ34aCpJhCi7UmHNL9qA7fMRw92BDDYmN6TUfDVbHZBsH7hQw6WugmeDST8EUt4gGj" +
            "HipI14EmgY8HBCyUOgoQGjnPW+weiMx4s9ugbL4HjppSbLMBKq0MSlvQnXuFTJuqud5PT32MoPFhCvX1ZmL5Xswx1VQ4IMJ0CDGFOduT" +
            "DgQKBgQDd/wc5R3KPGhR5yD6GAz5saldSokNNkvm/fjUtCF5nlau7GpXbEJ8HWZPep+InxpBKTBdKaISJ8TvwVyvLJaeRNF56KnZ6qquWm/" +
            "kkZHT+o5Osu7DRv3i+64pHrqKobgtMPdr+7VwqIRyHwyN6OqbjddXDibYi4GPIggcyrICkbQKBgQDN+KeaAIE4/7wuJpYCt9aST2nDYoesU" +
            "TsfdxqWRudg6Pvz8HNsGEjemnATGkWYnNAWhLQPKDtkMWD9d0K2eeQBBjabHxOE9d21ZfrdogZIDTKANvzjuPgDmhgAHGUwazlqGGbTkHHQxP" +
            "ypGODq/duVxJLn5JMKzQflPJQ9GiyPPQKBgEK11ofh7o0OO0T1ZcSDyQhvVFpipHL1TdO7q2Mi9quOEJMjRBEWh5N/2a25KX7mbonFH+KeUeXZ9" +
            "jcdGu3rHNXHtPRlOkFWdEXIDTBgd7/ljAUGSfZd4X0N7vnngr22g/KfS+X7kgye8qHhhBHA+lG0IU6nXXJRl+qzGFKj/+xpAoGBALHksATvqKwtHD+T" +
            "7f48/2Lwmb057UokAndjrB/c71wg/fMrWtUiiFVSd7fX+gIcdhDs3oM00U7w4v8nsQPfdNB5qFID3hz5KVrR0ID5vokedN6OH48jI3og7deyrqbKBRWgK" +
            "7dQSyzv5fS6WxA63xuUYvlLzsAGY2pXDwVTBOdVAoGAZW51yKC8emwOdQa9crxqmFm0YpH/XyO+Fcicy3oxBcb0PiCjg9PPsexJRg/PVTt0T7/g4aiennpwir" +
            "rDGPMZVrQW7SAseXrWlCInqJkk7FhP//Boo455XAeAzQ34oZUbA/VdmzssEf134fV6fK6uWh8sKWNHEmFVyWXRyv7XzD8=";

    //默认的Token（JWT）超时时间，一天
    public  static final Integer DEFAULT_EXPIRE_DAY = 1;
}
