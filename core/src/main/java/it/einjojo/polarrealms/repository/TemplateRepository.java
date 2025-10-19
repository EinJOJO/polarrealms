package it.einjojo.polarrealms.repository;

import it.einjojo.polarrealms.template.Template;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;

@NullMarked
public interface TemplateRepository {

    Optional<Template> loadByName(String name);

    void save(Template template);

    void deleteByName(String name);

    List<String> loadAllNames();

}
