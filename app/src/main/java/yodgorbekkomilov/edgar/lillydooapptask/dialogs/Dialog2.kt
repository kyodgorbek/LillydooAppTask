package yodgorbekkomilov.edgar.lillydooapptask.dialogs


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import yodgorbekkomilov.edgar.lillydooapptask.R

import kotlinx.android.synthetic.main.dialog_two.*

class Dialog2(private val handler: IDialog2): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phone_img.setOnClickListener {
            handler.OnPhoneClicked()
            dismiss()
        }

        email_img.setOnClickListener {
            handler.OnEmailClicked()
            dismiss()
        }

        msg_img.setOnClickListener {
            handler.OnMessageClicked()
            dismiss()
        }

    }

}