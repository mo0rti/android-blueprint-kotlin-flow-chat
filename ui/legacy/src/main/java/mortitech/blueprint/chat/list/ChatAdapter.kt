package mortitech.blueprint.chat.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mortitech.blueprint.chat.data.ChatMessage
import mortitech.blueprint.chat.databinding.ChatMessageIncomingItemBinding
import mortitech.blueprint.chat.databinding.ChatMessageOutgoingItemBinding

class ChatAdapter : ListAdapter<ChatMessage, RecyclerView.ViewHolder>(DiffCallback) {

    companion object {
        private const val VIEW_TYPE_INCOMING = 0
        private const val VIEW_TYPE_OUTGOING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_INCOMING -> IncomingMessageViewHolder(
                ChatMessageIncomingItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            VIEW_TYPE_OUTGOING -> OutgoingMessageViewHolder(
                ChatMessageOutgoingItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when (holder.itemViewType) {
            VIEW_TYPE_INCOMING -> (holder as IncomingMessageViewHolder).bind(message)
            VIEW_TYPE_OUTGOING -> (holder as OutgoingMessageViewHolder).bind(message)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.messageType == ChatMessage.MessageType.INCOMING) {
            VIEW_TYPE_INCOMING
        } else {
            VIEW_TYPE_OUTGOING
        }
    }

    fun addMessage(message: ChatMessage) {
        submitList(currentList + message)
    }

    inner class IncomingMessageViewHolder(private val binding: ChatMessageIncomingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: ChatMessage) {
            binding.message = message
            binding.executePendingBindings()
        }
    }

    inner class OutgoingMessageViewHolder(private val binding: ChatMessageOutgoingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: ChatMessage) {
            binding.message = message
            binding.executePendingBindings()
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem == newItem
        }
    }
}
