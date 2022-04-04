package id.ac.fksp.spmi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementUpdateRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String contet;
    private MultipartFile image;
    private MultipartFile pdf;
}
