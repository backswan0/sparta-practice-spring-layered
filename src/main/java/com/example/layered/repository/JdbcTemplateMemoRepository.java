package com.example.layered.repository;

import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateMemoRepository implements MemoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public MemoResponseDto saveMemo(Memo memo) {
        // INSERT Query를 직접 작성하지 않아도 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("memo").usingGeneratedKeyColumns("id");
        /*
        1. memo라는 테이블에 insert를 하겠다.
        2. 테이블의 key 값은 id로 설정되어 있다.
         */

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("title", memo.getTitle());
        parameters.put("contents", memo.getContents());
        /*
        1. 이 파라미터로 title이라는 컬럼에는 memo의 title을 삽입한다.
        2. 이 파라미터로 contents라는 컬럼에는 memo의 contents를 삽입한다.
         */

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        // 저장 후 생성된 key 값(== id 값)을 Number 타입으로 반환하는 메서드

        return new MemoResponseDto(key.longValue(), memo.getTitle(), memo.getContents());
        /*
        return new MemoResponseDto(key.longValue(), memo.getTitle(), memo.getContents());
        위와 같이 쓰면 생성자가 없어서 빨간 줄 뜬다.
        반드시 MemoResponseDto 클래스로 가서 AllArgsConstructor annotation을 추가해야 한다.
        그렇게 하면 빨간 줄이 사라진다.
         */
    }

    @Override
    public List<MemoResponseDto> findAllMemos() {
        return jdbcTemplate.query("SELECT * FROM memo", memoRowMapper());
        // memoRowMapper() 메서드는 따로 만들어주어야 한다.
    }

    @Override
    public Optional<Memo> findMemoById(Long id) {
        List<Memo> result = jdbcTemplate.query("SELECT * FROM memo WHERE id = ?", memoRowMapperV2(), id);
        return result.stream().findAny();
        /*
        [수정 전] public Memo findMemoById(Long id)
        [수정 후] public Optional<Memo> findMemoById(Long id)
        [설명]
        - 맨 뒤에 있는 id값이 앞에 있는 물음표, 일명 placeholder와 치환되면서 값이 들어간다.
        - memoRowMapperV2() 또한 먼저 이름을 짓고 메서드 생성해야 한다.
        - 메서드까지 아래 모두 만든 다음에 option+command+v 단축키로 변수 생성하자.
        - List 형태를 Optional로 바꿔야 해서 stream().findAny() 사용한다.
        -  그다음 MemoServiceImpl로 가자. 빨간 줄이 떴을 테니까.....
         */
    }

    @Override
    public Memo findMemoByIdOrElseThrow(Long id) {
        List<Memo> result = jdbcTemplate.query("SELECT * FROM memo WHERE id = ?", memoRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public int updateMemo(Long id, String title, String contents) {
        int updatedRow = jdbcTemplate.update("UPDATE memo SET title = ?, contents = ? WHERE id = ?", title, contents, id);
        /*
        - 위에 작성한 쿼리가 반영된 row의 수가 int로 반환된다.
          따라서 int 타입 변수 updatedRow에 저장된다.
        - 각 물음표, 일명 placeholder에 맞는 파라미터를 넣어주면 된다.
         */
        return updatedRow;
    }

    @Override
    public int updateTitle(Long id, String title) {
        int updatedTitle = jdbcTemplate.update("UPDATE memo SET title = ? WHERE id = ?", title, id);
        return updatedTitle;
    }

    @Override
    public int deleteMemo(Long id) {
        int updatedRow2 = jdbcTemplate.update("DELETE FROM memo WHERE id = ?", id);
        return updatedRow2;
        /*
        [수정 전] void
        [수정 후] int
        [주의] 메서드 이름이 delete가 아니라 update인 점을 놓치지 말자.
         */
    }

    private RowMapper<MemoResponseDto> memoRowMapper() {
        // 클래스 내부에서만 사용하므로 private
        return new RowMapper<MemoResponseDto>() {
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new MemoResponseDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("contents")
                );
            }
        };
    }

    private RowMapper<Memo> memoRowMapperV2 () {
        return new RowMapper<Memo>() {
            @Override
            public Memo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Memo(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("contents")
                );
            }
        };
        // 빨간 줄이 생긴 부분에 세미콜론(;)을 추가한다.
    }
}