package com.matanh.transfer.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.matanh.transfer.R
import com.matanh.transfer.server.ChatMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatAdapter : ListAdapter<ChatMessage, ChatAdapter.ChatViewHolder>(ChatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvText: TextView = itemView.findViewById(R.id.tvChatText)
        private val tvTime: TextView = itemView.findViewById(R.id.tvChatTime)
        private val btnCopy: android.widget.ImageButton = itemView.findViewById(R.id.btnCopyMessage)
        private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        fun bind(msg: ChatMessage) {
            tvText.text = msg.text
            tvTime.text = dateFormat.format(Date(msg.timestamp))
            
            btnCopy.setOnClickListener {
                val clipboard = itemView.context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText("Chat Message", msg.text)
                clipboard.setPrimaryClip(clip)
                android.widget.Toast.makeText(itemView.context, "Message copied", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

    class ChatDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem.timestamp == newItem.timestamp && oldItem.text == newItem.text
        }

        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem == newItem
        }
    }
}
