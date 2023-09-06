/*

import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

interface MyApi {
    // Firebase "snapshots"
    // val jobOffers: Flow<List<JobOffer>>
    suspend fun getJobOffer(withText: String): List<JobOffer>
}

class MyApiImplemWithFirestore : MyApi {
    override suspend fun getJobOffer(withText: String): List<JobOffer> {
        return firestore.collection("jobs").where(description, "==", withText).get();
    }
}

data class JobOffer(

    val description: String
) {
}

class MyComposableStateHandler(
    val api: MyApi,
    val coroutineScope: CoroutineScope,
) {
    var jobOffer by mutableStateOf("")
    private val _fromApi = api.jobOffers
    private var _jobs: MutableState<List<JobOffer>> = mutableStateOf(emptyList())
    private var _searchValue: MutableState<String> = mutableStateOf("")

    val jobs: List<JobOffer>
        get() = _fromApi.filter { it.description.contains(searchValue) }.collectAsState(emptyList()).value

    val searchValue = _searchValue.value

    fun onSearchInput(newSearchValue: String) {
        _searchValue.value = newSearchValue
    }

    fun onSearchClick(text: String) {
        coroutineScope.launch {
            _jobs.value = api.getJobOffer(text);
        }
    }

    fun onButtonClick() {
        coroutineScope.launch {
            api.applyForJob("myJobId")
        }
    }
}

fun rememberMyState(
     api: MyApi,
    ): MyComposableStateHandler {
    val coroutineScope = rememberCoroutineScope()

    return remember {
        MyComposableStateHandler(api, coroutineScope)
    }
}

@Composable
fun MyComposableStateful(
    userId: String,
    api: MyApi,
) {
    val state = rememberMyState(api)

    if (state.isLoading) {
        ShowSpinner()
    } else {
        MyComposableStateless(
            jobs = state.jobs,
            searchValue = state.searchValue,
            onSearchInput = {state.onSearchInput(it) },
        )
    }
}

@Composable
fun MyComposableStateless(
    jobs: List<JobOffer>,
    searchValue: String,
    onSearchInput: (String) -> Unit,
) {
    TextField(value = searchValue, onValueChange = { onSearchInput(it) })

    jobs.forEach {
        JobCard(it)
    }
}

@Composable
fun JobCard(job: JobOffer) {
    Text("Hi there")
}

@Preview
fun kaljsdhf() {
    MyComposableStateless(
        "mon texte de boutton",
        {},
        "job descr",
    )
}

 */