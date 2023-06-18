package org.springframework.samples.nt4h.constants

import org.springframework.ui.Model
import org.springframework.ui.ModelMap

class MessageConstants {

    companion object {

        // Achievement
        const val DELETE_SUCCESS_ACHIEVEMENT = "Achievement deleted successfully"
        const val DELETE_ERROR_ACHIEVEMENT = "Achievement could not be deleted"
        const val CREATE_SUCCESS_ACHIEVEMENT = "Achievement created successfully"
        const val CREATE_ERROR_ACHIEVEMENT = "Achievement could not be created"
        const val UPDATE_SUCCESS_ACHIEVEMENT = "Achievement updated successfully"
        const val UPDATE_ERROR_ACHIEVEMENT = "Achievement could not be updated"

        // Lobby
        const val DELETE_SUCCESS_LOBBY = "Lobby deleted successfully"
        const val DELETE_ERROR_LOBBY = "Lobby could not be deleted"
        const val CREATE_SUCCESS_LOBBY = "Lobby created successfully"
        const val CREATE_ERROR_LOBBY = "Lobby could not be created"
        const val UPDATE_SUCCESS_LOBBY = "Lobby updated successfully"
        const val UPDATE_ERROR_LOBBY = "Lobby could not be updated"


        @JvmStatic
        fun addError(model: ModelMap, message: String) {
            model["error"] = message
        }

        @JvmStatic
        fun addMessage(model: ModelMap, message: String) {
            model["message"] = message
        }



    }
}
