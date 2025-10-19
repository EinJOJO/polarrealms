package it.einjojo.polarrealms.repository.postgres;

import it.einjojo.polarrealms.repository.TemplateRepository;
import it.einjojo.polarrealms.template.Template;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PGTemplateRepository implements TemplateRepository {
    private final DataSource dataSource;

    public PGTemplateRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Template> loadByName(String name) {
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement("")) {

        } catch (SQLException e) {

        }
        return Optional.empty();
    }

    @Override
    public void save(Template template) {

    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public List<String> loadAllNames() {
        return List.of();
    }
}
