import com.antelopesystem.crudframework.utils.component.componentmap.annotation.ComponentMap
import com.antelopesystem.crudframework.utils.component.componentmap.annotation.ComponentMapKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

interface TestHandler {
    @get:ComponentMapKey
    val type: String
}

class TestImpl1 : TestHandler {
    override val type: String
        get() = "Test1"
}

class TestImpl2 : TestHandler {
    override val type: String
        get() = "Test2"
}

class TestMapUser {
    @ComponentMap
    lateinit var map: Map<String, TestHandler>
}

@Configuration
class ComponentMapTestConfig {
    @Bean
    fun exampleImpl1() = TestImpl1()

    @Bean
    fun exampleImpl2() = TestImpl2()

    @Bean
    fun testMapUser() = TestMapUser()
}