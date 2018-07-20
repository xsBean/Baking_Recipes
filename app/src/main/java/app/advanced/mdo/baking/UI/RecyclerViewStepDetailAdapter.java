package app.advanced.mdo.baking.UI;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import app.advanced.mdo.baking.Models.bakingStep;
import app.advanced.mdo.baking.R;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.SimpleExoPlayerViewHelper;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

public class RecyclerViewStepDetailAdapter extends Adapter<RecyclerViewStepDetailAdapter.StepDetailViewHolder> {

    private final List<bakingStep> mySteps;
    private int currentPosition;
    private Context context;

    public RecyclerViewStepDetailAdapter(List<bakingStep> mySteps, int currentPosition,  Context context) {
        this.mySteps = mySteps;
        this.currentPosition = currentPosition;
        this.context = context;
    }

    @NonNull
    @Override
    public StepDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_details, parent, false);
        return new StepDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepDetailViewHolder holder, int position) {

        holder.step = mySteps.get(position);
//        holder.bind(Uri.parse(holder.step.getVideoURL()));
//        initializePlayer(holder,mySteps.get(position).getVideoURL());
        holder.longDescriptionTV.setText(holder.step.getShortDescription());

        // Play the video when user chooses this step
//        if(currentPosition == position )
//            holder.exoPlayer.setPlayWhenReady(true);
    }

    @Override
    public int getItemCount() {

        return mySteps.size() ;
    }
    private void initializePlayer(StepDetailViewHolder holder, String recipeUri) {

        // Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        //Initialize the player
        holder.exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        // Initialize simpleExoPlayerView
        holder.myPlayerView.setPlayer(holder.exoPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context,"ExoPlayer"));
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource videoSource = new ExtractorMediaSource(
                Uri.parse(recipeUri),
                dataSourceFactory,
                extractorsFactory,
                null,
                null);

//        holder.exoPlayer.prepare(videoSource);
    }
    public class StepDetailViewHolder extends RecyclerView.ViewHolder{
        private bakingStep step;
        private SimpleExoPlayer exoPlayer;
//        private SimpleExoPlayerViewHelper helper;
        private SimpleExoPlayerView myPlayerView;
        private TextView longDescriptionTV;
//        private Uri mediaUri;
        public StepDetailViewHolder(View itemView) {
            super(itemView);
            myPlayerView = itemView.findViewById(R.id.exoplayer_view);
            longDescriptionTV = itemView.findViewById(R.id.tv_long_description);
        }

//        @NonNull
//        @Override
//        public View getPlayerView() {
//            return myPlayerView;
//        }
//
//        @NonNull
//        @Override
//        public PlaybackInfo getCurrentPlaybackInfo() {
//            return helper !=null ? helper.getLatestPlaybackInfo() : new PlaybackInfo();
//        }
//
//        @Override
//        public void initialize(@NonNull Container container, @Nullable PlaybackInfo playbackInfo) {
//            if(helper == null){
//                helper = new SimpleExoPlayerViewHelper(container, this, mediaUri);
//            }
//            helper.initialize(playbackInfo);
//        }
//
//        @Override
//        public void play() {
//            if(helper !=null) helper.play();
//        }
//
//        @Override
//        public void pause() {
//            if(helper !=null) helper.pause();
//        }
//
//        @Override
//        public boolean isPlaying() {
//            return helper!= null && helper.isPlaying();
//        }
//
//        @Override
//        public void release() {
//            if(helper != null){
//                helper.release();
//                helper = null;
//            }
//        }
//
//        @Override
//        public boolean wantsToPlay() {
//            return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
//        }
//
//        @Override
//        public int getPlayerOrder() {
//            return getAdapterPosition();
//        }
//        void bind(Uri media){
//            this.mediaUri = media;
//        }
    }

}
