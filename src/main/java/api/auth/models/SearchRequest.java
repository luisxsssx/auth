package api.auth.models;

import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    private String type;
    private String value;

}