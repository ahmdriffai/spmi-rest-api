package id.ac.fksp.spmi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementSaveRequest {

    @NotBlank
    @NotNull
    private String title;
    @NotBlank
    @NotNull
    private String content;
    private MultipartFile image;
    private MultipartFile pdf;
}
