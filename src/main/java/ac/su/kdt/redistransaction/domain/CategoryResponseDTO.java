package ac.su.kdt.redistransaction.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private List<Map<String, String>> parent;
    private int depth;

    public static CategoryResponseDTO fromCategory(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setParent(category.getParentsAsc());
        dto.setDepth(category.getDepth());
        return dto;
    }

    @Override
    public String toString() {
        return "CategoryResponseDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", parent=" + parent +
            ", depth=" + depth +
            '}';
    }
}
