package id.ac.fksp.spmi.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3ClientConfig {

    @Value("${s3.do.accessKey}")
    private String s3SpaceKey;

    @Value("${s3.do.secretKey}")
    private String s3SpaceSecret;

    @Value("${s3.do.endpoint}")
    private String s3SpaceEndpoint;

    @Value("${s3.do.region}")
    private String s3SpaceRegion;

    @Bean
    public AmazonS3 getS3(){
        BasicAWSCredentials credentials= new BasicAWSCredentials(s3SpaceKey,s3SpaceSecret);
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3SpaceEndpoint, s3SpaceRegion))
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }
}
