package moe.caa.multilogin.core.skinrestorer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import moe.caa.multilogin.api.auth.GameProfile;
import moe.caa.multilogin.api.auth.Property;
import moe.caa.multilogin.core.configuration.yggdrasil.YggdrasilServiceConfig;
import moe.caa.multilogin.core.ohc.LoggingInterceptor;
import moe.caa.multilogin.core.ohc.RetryInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

public class SkinRestorerCore {
    private static PublicKey publicKey;

    static {
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(new byte[]{48, -126, 2, 34, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -126, 2, 15, 0, 48, -126, 2, 10, 2, -126, 2, 1, 0, -54, 80, 120, 7, -87, -71, -105, 62, -29, -62, -73, 5, -49, -95, 93, -3, -7, -33, 82, 23, 47, -105, 27, 19, 74, 126, 100, 32, -81, -10, 104, 97, -75, 11, 121, 12, -53, -123, 99, -121, 9, 113, 23, -96, 69, 81, 41, -33, -58, 19, 108, -64, -8, -31, -35, -104, -83, -112, 28, 68, 0, 86, 54, -26, -20, -28, 36, 84, 112, 31, 5, 64, -10, 103, 112, -115, -85, 33, 92, -126, -19, 71, -93, -86, 116, -75, -41, 21, 92, -108, 60, -123, 17, 44, -28, -106, -88, 99, 65, -75, -67, 60, 16, -34, 95, 50, 113, -68, -120, 82, -32, 16, -110, 1, 51, 27, 61, 6, -15, 119, -77, 127, -59, -122, -31, -14, 60, -88, -107, 77, -103, -105, 99, 69, -104, -31, -100, -104, -86, -6, 10, 71, -27, -29, 105, 52, 79, 62, -16, 108, 13, -82, 22, 35, 107, -75, -49, 21, -52, 120, 121, 104, 117, 61, -27, -100, 44, -43, -65, 35, -104, 4, -75, -53, -24, 34, 96, 7, 43, -114, -59, -57, 9, -14, 25, -41, 124, 95, 114, 99, -127, 116, 105, -30, -12, -13, 66, 115, -1, 127, -124, 4, -47, -68, 68, -18, 1, -54, 27, 65, 127, -125, 114, -16, -44, 58, -78, -43, -84, -115, -9, -108, 34, -15, -5, 109, 79, -8, -52, 38, 28, 96, -22, -72, 90, -78, 39, 92, 122, -110, -14, -86, -18, 14, 98, 37, -20, -2, 87, 92, 103, 26, 110, -20, -48, -78, -45, -33, -2, 29, -126, 68, 5, 41, 63, -13, -27, 29, 119, 12, -106, -9, -80, -114, 97, -108, -24, -57, -61, 43, -23, 98, 125, 39, 13, 99, 8, -49, -59, 26, 56, -100, -92, -58, 21, -105, -1, -47, -86, -121, 10, 31, -60, 2, -60, 50, 44, -86, -50, 33, -124, 68, -46, 80, -58, -35, -68, -63, -121, -54, 32, 32, -62, -64, -70, 11, 0, 124, 22, -6, 51, 121, -86, 10, -94, -102, 76, -64, 31, -3, 80, 35, -121, -111, 69, 38, 120, -46, -5, -74, -60, -7, -74, 68, 87, 74, -80, 107, 29, 66, 18, -125, -49, 36, -67, 95, 33, 8, -50, 75, -52, -29, -45, -50, -96, -114, -111, -111, 10, -44, -78, -9, -26, -45, 61, 55, -45, 71, -12, -119, -7, 85, 1, -28, 109, -120, 120, 34, 45, -112, -47, -23, 37, 125, -78, -126, -120, -59, 103, -39, -7, 22, 120, -5, 71, -42, 103, 56, -49, -117, -14, 124, -120, -116, -5, -73, 105, -29, -17, -75, -54, -41, -105, 34, -31, 31, -8, 111, 57, -14, 14, -116, -4, 78, -106, -91, -66, 59, -108, 48, 72, 83, -8, 56, 71, 50, 44, 5, 32, 13, -43, 32, -55, 109, -6, 103, -103, -127, -37, -50, 110, 41, 111, -30, -126, 28, -39, -80, -28, -34, -67, 85, 6, -39, 12, 3, 34, -45, -101, 33, 94, -94, -15, 16, -79, 21, -77, 118, -96, 49, -14, 61, -93, -71, -113, 91, 83, 104, 19, 114, 86, -33, 2, 3, 1, 0, 1});
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            SkinRestorerCore.publicKey = keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("An exception occurred during the generation of yggdrasil session public key.", e);
        }
    }

    private final YggdrasilServiceConfig yggdrasilServiceConfig;
    private final OkHttpClient okHttpClient;
    private final GameProfile profile;

    public SkinRestorerCore(YggdrasilServiceConfig yggdrasilServiceConfig, GameProfile profile) {
        this.yggdrasilServiceConfig = yggdrasilServiceConfig;
        this.profile = profile.clone();
        this.okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new RetryInterceptor(yggdrasilServiceConfig.getSkinRestorer().getRetry(),
                        yggdrasilServiceConfig.getSkinRestorer().getRetryDelay()))
                .addInterceptor(new LoggingInterceptor())
                .writeTimeout(Duration.ofMillis(yggdrasilServiceConfig.getSkinRestorer().getTimeout()))
                .readTimeout(Duration.ofMillis(yggdrasilServiceConfig.getSkinRestorer().getTimeout()))
                .connectTimeout(Duration.ofMillis(yggdrasilServiceConfig.getSkinRestorer().getTimeout()))
                .proxy(yggdrasilServiceConfig.getSkinRestorer().getProxy().getProxy())
                .proxyAuthenticator(yggdrasilServiceConfig.getSkinRestorer().getProxy().getProxyAuthenticator())
                .build();
    }

    @SneakyThrows
    public SkinRestorerResult doRestorer() {
        Map<String, Property> propertyMap = profile.getPropertyMap();
        if (propertyMap == null || !propertyMap.containsKey("textures")) {
            return SkinRestorerResult.ofNoSkin();
        }
        Property textures = propertyMap.get("textures");
        JsonObject jsonObject = JsonParser.parseString(new String(Base64.getDecoder().decode(textures.getValue()), StandardCharsets.UTF_8)).getAsJsonObject();
        if (!jsonObject.has("textures")
                && !jsonObject.getAsJsonObject("textures").has("SKIN")
                && !jsonObject.getAsJsonObject("textures").getAsJsonObject("SKIN").has("url")
        ) {
            return SkinRestorerResult.ofNoSkin();
        }
        JsonObject skinData = jsonObject.getAsJsonObject("textures").getAsJsonObject("SKIN").getAsJsonObject();
        String url = skinData.getAsJsonPrimitive("url").getAsString();
        // todo 查询缓存
        boolean slim = skinData.has("metadata")
                && skinData.getAsJsonObject("metadata").has("model")
                && skinData.getAsJsonObject("metadata").getAsJsonPrimitive("model").getAsString().equals("slim");
        if (isSignatureValid(textures.getValue(), textures.getSignature())) {
            // todo 材质域名白名单判断
            return SkinRestorerResult.ofSignatureValid();
        }
        byte[] bytes;
        try {
            bytes = requireValidSkin(url, slim);
        } catch (Exception e) {
            return SkinRestorerResult.ofBadSkin(e);
        }
        // todo 开始修复

        return SkinRestorerResult.ofNoRestorer();
    }

    private byte[] requireValidSkin(String skinUrl, boolean slim) throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(skinUrl)
                .build();
        // 下载皮肤原件
        byte[] bytes = Objects.requireNonNull(okHttpClient.newCall(request).execute().body()).bytes();
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)){
            BufferedImage image = ImageIO.read(bais);

            boolean x64 = false; // 1.8 新版皮肤
            if (image.getWidth() != 64) {
                throw new SkinRestorerException("Skin width is not 64.");
            }
            if (!(image.getHeight() == 32 || image.getHeight() == 64)) {
                throw new SkinRestorerException("Skin height is not 64 or 32.");
            }
            x64 = image.getHeight() == 64;
            // TODO: 2022/7/13 皮肤半透明判断

            return bytes;
        }
    }

    private boolean isSignatureValid(String value, String signatureValue) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(publicKey);
        signature.update(value.getBytes());
        return signature.verify(Base64.getDecoder().decode(signatureValue));
    }
}
