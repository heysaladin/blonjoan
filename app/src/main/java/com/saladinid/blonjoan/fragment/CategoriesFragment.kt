package com.saladinid.blonjoan.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.saladinid.blonjoan.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CategoriesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment: DialogFragment() {

    // TODO: Rename and change types of parameters
    private
    var mParam1: String ? = null
    private
    var mParam2: String ? = null

    private
    var mListener: OnFragmentInteractionListener ? = null

    internal
    var gridLayout: GridLayout ? = null

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup ?, savedInstanceState : Bundle ? ): View ? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_categories, container, false)

        this.gridLayout = view.findViewById < View > (R.id.mainGrid) as GridLayout

        setSingleEvent(this.gridLayout!!)

        return view
    }

    // we are setting onClickListener for each element
    private fun setSingleEvent(gridLayout: GridLayout) {
        for (i in 0 until gridLayout.childCount) {
            val cardView = gridLayout.getChildAt(i) as CardView
            cardView.setOnClickListener {
                val dialogFragment = this@CategoriesFragment
                dialogFragment.dismiss()
                Toast.makeText(activity, "Clicked at index $i", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }


    override fun onResume() {
        super.onResume()
        val params = activity!!.window!!.attributes
        params.width = LinearLayout.LayoutParams.MATCH_PARENT
        params.height = LinearLayout.LayoutParams.MATCH_PARENT
        activity!!.window!!.attributes = params as android.view.WindowManager.LayoutParams
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoviesFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): CategoriesFragment {
            val fragment = CategoriesFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

} // Required empty public constructor