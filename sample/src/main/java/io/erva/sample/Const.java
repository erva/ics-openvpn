package io.erva.sample;

public interface Const {
    String config = "dev tun\n" +
            "client\n" +
            "\n" +
            "<connection>\n" +
            "remote vpnkh.sigma.software 1194 udp\n" +
            "</connection>\n" +
            "\n" +
            "\n" +
            "\n" +
            "cipher AES-256-CBC\n" +
            "auth SHA256\n" +
            "comp-lzo no\n" +
            "tls-client\n" +
            "tls-version-min 1.2\n" +
            "tls-cipher TLS-ECDHE-RSA-WITH-AES-128-GCM-SHA256:TLS-ECDHE-ECDSA-WITH-AES-128-GCM-SHA256:TLS-ECDHE-RSA-WITH-AES-256-GCM-SHA384:TLS-DHE-RSA-WITH-AES-256-CBC-SHA256\n" +
            "verify-x509-name S19ma_0VPN name-prefix\n" +
            "remote-cert-eku \"TLS Web Server Authentication\"\n" +
            "verb 3\n" +
            "auth-user-pass\n" +
            "push-peer-info\n" +
            "explicit-exit-notify\n" +
            "#resolv-retry 3\n" +
            "resolv-retry infinite\n" +
            "#persist-key\n" +
            "#persist-tun\n" +
            "#\n" +
            "#\n" +
            "<tls-auth>\n" +
            "-----BEGIN OpenVPN Static key V1-----\n" +
            "555c71ad58b5405137ff733e815994cf\n" +
            "7913e90ffa68a5eedb6a68f5cee968a3\n" +
            "8f51978162eddd550c8b2596fcfa9bfb\n" +
            "6804e25cf79b64c2a8409c28713eaaea\n" +
            "6714110766c974c17cd8d9e36129baf2\n" +
            "2b07239ac335e9369cfd2b67afe8c892\n" +
            "ff3f550cf74a5ff7a7bd9770d4a3f08a\n" +
            "2494a5e2f171f1e23c6a485a8327abc0\n" +
            "d45e8ca66822208675dd103ccf8a50a0\n" +
            "51585d217b42be876848b37ee2cd6c01\n" +
            "302fe49e165d852caa6c289116a515d7\n" +
            "ea04620c35f610ba5cafa74018c2ded1\n" +
            "830bdd34268ce68b45b50d0aec6ad295\n" +
            "e780115ebcd7ae5a89ad7ab9e916cb34\n" +
            "7c339df2cdbc1b581a595e23985a227c\n" +
            "d089c681fc2323917f2a1236560b2006\n" +
            "-----END OpenVPN Static key V1-----\n" +
            "</tls-auth>\n" +
            "key-direction 1\n" +
            "#\n" +
            "<dh>\n" +
            "-----BEGIN DH PARAMETERS-----\n" +
            "MIIBCAKCAQEA+8o2dCXl1LweQYKsRlZzIpxWE3ZKvnpFGuJPhDkCcd5ZiNtPsRuo\n" +
            "wukXe+kAzoUb9f0OvGdOjDdR1164E/Ski+iX2eQ6I4ecWDPRlP9FX5+0QfTAOcHo\n" +
            "TZOE2U8KZH7uaPUBK5ntfebP6tExf2n5MXkMrJ2qKuWaMjitdPODID+WtKcDrSyu\n" +
            "MTnQQ2VbK8Y+ZMJ7rs+ZLuNLqqIBl6fY29/dA0VN1Qzn54iXUcyZ9LyAGpRdKAUI\n" +
            "GX/VoHZblXvViTnummbQ9v3UZYc8eXiipewyglegKEo8V+kAfJMF2E5KJlRLyk+P\n" +
            "gkpqMC8CzvJqSsx2atFyGOd9vavlkYlSgwIBAg==\n" +
            "-----END DH PARAMETERS-----\n" +
            "</dh>\n" +
            "#\n" +
            "<ca>\n" +
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIE3zCCA8egAwIBAgIJAO8QkNTn8FhSMA0GCSqGSIb3DQEBCwUAMIGlMQswCQYD\n" +
            "VQQGEwJVQTELMAkGA1UECBMCS0gxEDAOBgNVBAcTB0toYXJraXYxFjAUBgNVBAoT\n" +
            "DVNpZ21hU29mdHdhcmUxEDAOBgNVBAsTB1NFUlZFUlMxEDAOBgNVBAMUB0NBX09W\n" +
            "UE4xETAPBgNVBCkTCFNpZ21hVlBOMSgwJgYJKoZIhvcNAQkBFhl2cG4tYWRtaW5z\n" +
            "QHNpZ21hLnNvZnR3YXJlMB4XDTE2MDQwNDEzNTYwN1oXDTI2MDQwMjEzNTYwN1ow\n" +
            "gaUxCzAJBgNVBAYTAlVBMQswCQYDVQQIEwJLSDEQMA4GA1UEBxMHS2hhcmtpdjEW\n" +
            "MBQGA1UEChMNU2lnbWFTb2Z0d2FyZTEQMA4GA1UECxMHU0VSVkVSUzEQMA4GA1UE\n" +
            "AxQHQ0FfT1ZQTjERMA8GA1UEKRMIU2lnbWFWUE4xKDAmBgkqhkiG9w0BCQEWGXZw\n" +
            "bi1hZG1pbnNAc2lnbWEuc29mdHdhcmUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAw\n" +
            "ggEKAoIBAQDOnhwLpLu4oAtmce7gk1p4RN9YAAak3krBpSlmcy2W9b1LqkCnb/VA\n" +
            "s3tFRy9NcYsUsFfDYBwugQ1VNfwZX74ccL9RCR8sEICuedo1xP8RkDIWIKJYCiwU\n" +
            "9tPkx77U0prSmY03c2CMc2cRUCH31JeXZ7IQyIsbQFiu6PJZoxLt/uRsBBpHZShC\n" +
            "v7IbEw5ReMPkgNlcughaEqMvceDmk5RUQEjkYtuUOIO98FpoCamdyTv2Bxc9sLQd\n" +
            "guj2W/67uKYQXrweovKkAVn2oBQYOpJX3nUdjFYGf+rNODMNLwFmMR6Xyuy18cj9\n" +
            "ks6dU4OlrxbNIavz+uqcvpO2Rnyh5LVJAgMBAAGjggEOMIIBCjAdBgNVHQ4EFgQU\n" +
            "B4dWoxLmVIHbrsjJCRP/bztOYxAwgdoGA1UdIwSB0jCBz4AUB4dWoxLmVIHbrsjJ\n" +
            "CRP/bztOYxChgaukgagwgaUxCzAJBgNVBAYTAlVBMQswCQYDVQQIEwJLSDEQMA4G\n" +
            "A1UEBxMHS2hhcmtpdjEWMBQGA1UEChMNU2lnbWFTb2Z0d2FyZTEQMA4GA1UECxMH\n" +
            "U0VSVkVSUzEQMA4GA1UEAxQHQ0FfT1ZQTjERMA8GA1UEKRMIU2lnbWFWUE4xKDAm\n" +
            "BgkqhkiG9w0BCQEWGXZwbi1hZG1pbnNAc2lnbWEuc29mdHdhcmWCCQDvEJDU5/BY\n" +
            "UjAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBCwUAA4IBAQBYNZ+zgQT/1E4vwWxo\n" +
            "kdOc3LD6bF2HEjUOAeeeuWDIrPMCTAANwZGilU7LSWV7hQfyWqn/6PsRRkP4m8Lu\n" +
            "BLt2DfNTH1WaJ8KTiYqRz9a1hd0Y9gm5g/MBiFi90N9fGwUqEgtNkoEp+jMVFpho\n" +
            "k0GpixbtxCJEUuIAT+T69ZKNnPccmUh54152gWqmfIJd1Wk+GCCcwV8LPogddG2H\n" +
            "LHW0CoajK6NL9r4hwQp9Exy6PSqIE8d+a8hn0HCHA2bgQYVLIlH+Fdl6hCw8q1QX\n" +
            "hVTVR8FLkiiAKDfxG2K6TwUHDkvdmwQMWcsV0bf8TSJMoD7Y7alpIZTVoJvNnQDC\n" +
            "x90G\n" +
            "-----END CERTIFICATE-----\n" +
            "</ca>\n" +
            "#\n" +
            "<cert>\n" +
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIFAzCCA+ugAwIBAgICAPcwDQYJKoZIhvcNAQELBQAwgaUxCzAJBgNVBAYTAlVB\n" +
            "MQswCQYDVQQIEwJLSDEQMA4GA1UEBxMHS2hhcmtpdjEWMBQGA1UEChMNU2lnbWFT\n" +
            "b2Z0d2FyZTEQMA4GA1UECxMHU0VSVkVSUzEQMA4GA1UEAxQHQ0FfT1ZQTjERMA8G\n" +
            "A1UEKRMIU2lnbWFWUE4xKDAmBgkqhkiG9w0BCQEWGXZwbi1hZG1pbnNAc2lnbWEu\n" +
            "c29mdHdhcmUwHhcNMTYxMjAyMDk1OTMxWhcNMjYxMTMwMDk1OTMxWjCBgzELMAkG\n" +
            "A1UEBhMCVUExCzAJBgNVBAgTAktIMRAwDgYDVQQHEwdLaGFya2l2MRYwFAYDVQQK\n" +
            "Ew1TaWdtYVNvZnR3YXJlMRIwEAYDVQQLEwlFTVBMT1lFRVMxFjAUBgNVBAMTDW1h\n" +
            "eGltLnN1Y2hrb3YxETAPBgNVBCkTCFNpZ21hVlBOMIIBIjANBgkqhkiG9w0BAQEF\n" +
            "AAOCAQ8AMIIBCgKCAQEAx9H4+iSLVyWJNISrsK7ufO9DSWSV8sx0svsbn4g5UgCg\n" +
            "C4s7slYHAahQnlWh3cXhWhkwF4hbpt4JknRzQVfKqgwWPGo/qVHJ4uClQzyMNwD3\n" +
            "jX+j9A0wYQC6X81VCfXov9+YSISSLfGIq0VtHMvX9/xfeuLpIaQkRQGUR7U1t/Kc\n" +
            "iE9wX8AFSqDOZRIL73eYWn7VqxdZNIgzxIInJt7DnF6iNgxaXqEGVCtHtlyEA1kD\n" +
            "qSf6wnbCn7ur3yjTfkxctUhor+rwZaiHMbUTb8zT2nWXv5lpeJDynb5UcGhGeT5l\n" +
            "liYGjhAoRTm/WCdxxp3qMr2ewDsasuJDISmaOwDWnwIDAQABo4IBWzCCAVcwCQYD\n" +
            "VR0TBAIwADAsBglghkgBhvhCAQ0EHxYdT3BlblZQTiBHZW5lcmF0ZWQgQ2VydGlm\n" +
            "aWNhdGUwHQYDVR0OBBYEFK3VTlznsczmMBS1gCkosIlxOulkMIHaBgNVHSMEgdIw\n" +
            "gc+AFAeHVqMS5lSB267IyQkT/287TmMQoYGrpIGoMIGlMQswCQYDVQQGEwJVQTEL\n" +
            "MAkGA1UECBMCS0gxEDAOBgNVBAcTB0toYXJraXYxFjAUBgNVBAoTDVNpZ21hU29m\n" +
            "dHdhcmUxEDAOBgNVBAsTB1NFUlZFUlMxEDAOBgNVBAMUB0NBX09WUE4xETAPBgNV\n" +
            "BCkTCFNpZ21hVlBOMSgwJgYJKoZIhvcNAQkBFhl2cG4tYWRtaW5zQHNpZ21hLnNv\n" +
            "ZnR3YXJlggkA7xCQ1OfwWFIwEwYDVR0lBAwwCgYIKwYBBQUHAwIwCwYDVR0PBAQD\n" +
            "AgeAMA0GCSqGSIb3DQEBCwUAA4IBAQB87C1zdbIWzqXwTx8uFvqYiwL0zfjDUxq9\n" +
            "n3MsHTN9hQtbGGQr15Bq2cY5B7jRRtOrNUV5OBHjnO8HtBwIiwbdppfX8XAkd2s5\n" +
            "yA6pE02nfOtGd++SWvOy3jlNlzLvasf1KsKvptSX635Pm9b1umBcETFKgU8lFHcm\n" +
            "YYrI7bdLDQNN2DiWowwjuvpTBsU5ARIj60N9yUfgfOMPfisuDtpWWiXstvnIwxXc\n" +
            "oTSZ8R0RZWqt5EE587jkTLjYBJJHHvkL+Im9LvI8KkeL0Q9EdHLQl4eGg5iK9D7g\n" +
            "0WtR2Hkk+EvQdbMjpXGol3eRrFM2g/cc1o10OZjnOWJAVWFjYRfY\n" +
            "-----END CERTIFICATE-----\n" +
            "</cert>\n" +
            "#\n" +
            "<key>\n" +
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEAx9H4+iSLVyWJNISrsK7ufO9DSWSV8sx0svsbn4g5UgCgC4s7\n" +
            "slYHAahQnlWh3cXhWhkwF4hbpt4JknRzQVfKqgwWPGo/qVHJ4uClQzyMNwD3jX+j\n" +
            "9A0wYQC6X81VCfXov9+YSISSLfGIq0VtHMvX9/xfeuLpIaQkRQGUR7U1t/KciE9w\n" +
            "X8AFSqDOZRIL73eYWn7VqxdZNIgzxIInJt7DnF6iNgxaXqEGVCtHtlyEA1kDqSf6\n" +
            "wnbCn7ur3yjTfkxctUhor+rwZaiHMbUTb8zT2nWXv5lpeJDynb5UcGhGeT5lliYG\n" +
            "jhAoRTm/WCdxxp3qMr2ewDsasuJDISmaOwDWnwIDAQABAoIBACsGI+TAP5xZAazk\n" +
            "MA+P6tvNrKA93ydzLGFpPe5gp27noY46wXTJnyKVOh6Nc5fLxpTevYMjohBQ2KDH\n" +
            "IBXesE1i4trJScHMUXrfQdYzUEF7nNHDvOqMeHE6deaw0HGfmCjQHProSI1J51Jx\n" +
            "VWgQ4ASOO4S7HNVYgVK2X1X9R36+hVBF843uRQESQUm0Q0uOga568Vgf4EVLL8Su\n" +
            "VbqZcsib6BHTTgHuQSSe4EChGb15RZiP0esu5HcTjtNr7CaL0Zux2spsbAn3cZmV\n" +
            "o9uUWPUG4dX5dAeDvykAaZQyy/Eln6XuIXzuXtyWocW/4buxSyTcnUQvEV1GAAog\n" +
            "zU4js4ECgYEA+5rbsJUgwUnn6QKw7p1vlAY5YyaD5ZPkKDAygn4Q0bib2ozwerEl\n" +
            "q8iTUrZwWwayUM3vkF5bssf/7uo3Ye+jlB1r+bCLVEV9nmlOyZUhdahehF3imQkD\n" +
            "PN5YZYY3pBe9stCZ6wOjkwh+GJOS3pqTcWbVClzHhBbxWPkporLhFF8CgYEAy0+K\n" +
            "W2olltm/sVYDxkwQ9N9fMZ+H41w4MM8SHd3P0x66QlKaBGki+7hQHhT17o9GCZh8\n" +
            "rLxejli4vxkRdB/57et8mXHKWSgtdQA3vx6I0OsIBTvPfLGFf2GKNNvZ4JcjU2ht\n" +
            "1t9ily3dqTElyrZIEQWtN5QMK3BbkKj5LIsZZcECgYEAog2tAEAc98HFWCP+l8HZ\n" +
            "UurrXXw6Kc0mf+gDMF9IL/wKjiqG3U9p5wnqMbUGCur2QOtTKJZN04nTwysh/rdd\n" +
            "kZGdsRXPt7iGX1iOgXHHOkP2lKTvxQe0CSlgoAthnRQZf6mx6VfPrG0k4mlyiGPh\n" +
            "IYJNJz541w/75QWelodGR1sCgYBWgjvO9fWUoIaugHkOsWMtrrYkuUrq9Vx7eECv\n" +
            "MKiznomGmvsqEUpvbVe51RVoMtKMVrQGFAjv7NR/i70felOwZtkXuZFuKmhdNoCB\n" +
            "9A4nRzqcb3y0BB1XvDtAjqPAqs6/ONoz6c1booqE6YK5JKK5i37O/VHIUgDEJwIP\n" +
            "cAiYgQKBgQCkhSsaVJEW2H9zlFiNFlG50P/wEk0pl9njNyopDuimbGz6TlUvGwte\n" +
            "TIo8lUBTB+owX4Fu2Jr5AdFmuN9jI/+fR1b+CViZ/8v9A/2BarTciuA9ia1HSDOZ\n" +
            "obdGrBCX4EJm6zLvRth3ZkIn8mhsckCYyEEHuxqnW0wnBKnPfy6P4w==\n" +
            "-----END RSA PRIVATE KEY-----\n" +
            "</key>\n" +
            "\n";
}
