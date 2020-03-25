package com.nucleus.events.clubhub.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nucleus.events.clubhub.R;
import com.nucleus.events.clubhub.adapters.sliderAdapterHome;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Main_Home_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Main_Home_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Main_Home_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SliderView sliderView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Main_Home_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Main_Home_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Main_Home_Fragment newInstance(String param1, String param2) {
        Main_Home_Fragment fragment = new Main_Home_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
// mayue ap ne us din jo slider dala tha home activity me usko mene main_home_fragment me dal diya tha ohk..
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



// mayur plz rersolve the error
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main__home_, container, false);
        //here xml is linked


    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)//then this func is called thats it...
    {
        sliderView = (SliderView)getActivity().findViewById(R.id.imageSlider);

        sliderAdapterHome home = new sliderAdapterHome(getContext());
        sliderView.setSliderAdapter(home);
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        //try now
        // thanks your sir now its working
        // what was the error
        //it was returning null into slider because the xml was not linked in on create which was linking in on view create method
        //for which you have tofirst link has to be completed then image slider is found...
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
// ok thank you so much mayur and one more thing why it was not sliding
    // it might be because of the object not geeting called ... that view pager was derlared in wrong place maybe...
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
