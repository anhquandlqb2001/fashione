/*
 * Author: quanprolazer
 * Project: Fashione
 * An android shopping app writing in Kotlin
 */

package vn.quanprolazer.fashione.presentation.viewmodels

import androidx.lifecycle.*
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import vn.quanprolazer.fashione.domain.models.Message
import vn.quanprolazer.fashione.domain.models.Resource
import vn.quanprolazer.fashione.domain.repositories.MessageRepository
import vn.quanprolazer.fashione.presentation.utilities.LiveDataValidator
import vn.quanprolazer.fashione.presentation.utilities.LiveDataValidatorResolver
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val messageRepository: MessageRepository) :
    ViewModel() {
    val message: MutableLiveData<String> by lazy { MutableLiveData() }
    val messageValidator =
        LiveDataValidator(message).apply { //Whenever the condition of the predicate is true, the error message should be emitted
            addRule("Tin nhắn không được trống") { it.isNullOrBlank() }
        }

    val isMessageValid: MediatorLiveData<Boolean> by lazy {
        val liveDataMediator = MediatorLiveData<Boolean>()
        liveDataMediator.value = false

        liveDataMediator.addSource(message) { validateForm() }
        liveDataMediator
    }

    private fun validateForm() {
        val validators = listOf(messageValidator)
        val validatorResolver = LiveDataValidatorResolver(validators)
        isMessageValid.value = validatorResolver.isValid()
    }

    fun onSendMessage() {
        _status.value = Resource.Loading
        val messageOutgoing =
            Message(message = message.value.orEmpty(), createdAt = Timestamp.now())
        viewModelScope.launch {
            _status.value = messageRepository.sendMessage(messageOutgoing)
        }
    }

    private val _status: MutableLiveData<Resource<Message>> by lazy { MutableLiveData() }
    val status: LiveData<Resource<Message>> get() = _status

    private val _messages: MutableLiveData<Resource<List<Message>>> by lazy { MutableLiveData() }

    val messages: LiveData<Resource<List<Message>>> get() = _messages

    fun fetchMessage() {
        _messages.value = Resource.Loading
        viewModelScope.launch {
            _messages.value = messageRepository.getMessages()
        }
    }

    private val _recentlyIncomingMessage: MutableLiveData<Resource<Message>> by lazy {
        MutableLiveData()
    }

    val recentlyIncomingMessage: LiveData<Resource<Message>> get() = _recentlyIncomingMessage

    fun observeRecentlyIncomingMessage() {
        viewModelScope.launch {
            messageRepository.getRecentlyIncomingMessage().catch { Timber.e(it) }
                .collect { _recentlyIncomingMessage.value = it }
        }
    }
}