package ac.su.kdt.redistransaction.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter @Setter
public class Category {
    @Id
    private Long id;
    private String name;

    private int depth;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Category> children;

    @JsonIgnore
    public List<Map<String, String>> getParentsAsc() {
        List<Map<String, String>> parents = new ArrayList<>();
        Category currentParent = this.parent;
        while (currentParent != null) {
            parents.add(Map.of("/categories/"+((currentParent.getId())), currentParent.getName()));
            currentParent = currentParent.getParent();
        }
        Collections.reverse(parents);
        return parents;
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", parent=" + getParentsAsc() +
            ", depth=" + depth +
            '}';
    }
}
