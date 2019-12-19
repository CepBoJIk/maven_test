package ru.test.hello.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.test.hello.helpers.Helpers;
import ru.test.hello.interfaces.TacoRepository;
import ru.test.hello.models.Ingredient;
import ru.test.hello.models.Taco;
import ru.test.hello.models.TacoWithIngredients;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
@Slf4j
public class JDBCTacoRepository implements TacoRepository {

    private JdbcTemplate jdbc;
    private JDBCIngredientRepository jdbcIngredientRepository;

    @Autowired
    public JDBCTacoRepository(JdbcTemplate jdbcTemplate, JDBCIngredientRepository jdbcIngredientRepository) {
        this.jdbc = jdbcTemplate;
        this.jdbcIngredientRepository = jdbcIngredientRepository;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);

        taco.setId(tacoId);

        for (String ingredientId : taco.getIngredients()) {
            saveIngredientToTaco(ingredientId, tacoId);
        }

        return taco;
    }

    @Override
    public List<TacoWithIngredients> getTacos() {
        Iterable<Taco> tacos = jdbc.query(
            "select id, createdAt, name from Taco",
            this::mapRowToTaco
        );

        List<TacoWithIngredients> tacosWithIngredients = new ArrayList<>();

        for(Taco taco : tacos) {
            Iterable<String> ingredientsId = jdbc.query(
                    "select ingredient from Taco_Ingredients where taco=?",
                    this::mapRowIngredientsId,
                    taco.getId()
            );

            List<Ingredient> ingredients = jdbcIngredientRepository
                    .findAll(Helpers.getListFromIterable(ingredientsId));

            TacoWithIngredients tacoWithIngredients = new TacoWithIngredients();
            tacoWithIngredients.setId(taco.getId());
            tacoWithIngredients.setCreatedAt(taco.getCreatedAt());
            tacoWithIngredients.setName(taco.getName());
            tacoWithIngredients.setIngredients(ingredients);

            tacosWithIngredients.add(tacoWithIngredients);
        }

        return tacosWithIngredients;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());

        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP
        );

        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = preparedStatementCreatorFactory.newPreparedStatementCreator(
            Arrays.asList(
                taco.getName(),
                new Timestamp(taco.getCreatedAt().getTime())
            )
        );
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(psc, keyHolder); // it's hard as fuck!!!

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(String ingredientId, long tacoId) {
        jdbc.update(
            "insert into Taco_Ingredients (taco, ingredient) " +
                    "values (?, ?)",
            tacoId, ingredientId
        );
    }

    private Taco mapRowToTaco(ResultSet rs, int rowNum) throws SQLException {
        Taco taco = new Taco();

        taco.setId(rs.getLong("id"));
        taco.setCreatedAt(rs.getTime("createdAt"));
        taco.setName(rs.getString("name"));

        return taco;
    }

    private String mapRowIngredientsId(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString("ingredient");
    }

}
