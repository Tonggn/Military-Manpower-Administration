package com.toggn.mma.support.helper;

import com.toggn.mma.itp.client.EnterpriseClient;
import com.toggn.mma.itp.client.NoticeClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@SpringBootTest
@Sql(value = "/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class SpringBootTestHelper {

    @MockBean
    protected EnterpriseClient enterpriseClient;
    @MockBean
    protected NoticeClient noticeClient;
}
