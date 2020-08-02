package yodgorbekkomilov.edgar.lillydooapptask.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_one.*
import yodgorbekkomilov.edgar.lillydooapptask.R


class Dialog1(private val handler: IDialog1): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nein_button.setOnClickListener {
            handler.OnNeinClicked()
            dismiss()
        }

        ja_button.setOnClickListener {
            handler.OnJaClicked()
            dismiss()
        }

    }

}