//package com.test.testapplication.Utils;
//
//import android.media.MediaCodec;
//import android.media.MediaCodecInfo;
//import android.media.MediaFormat;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.util.Log;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//
//import static org.apache.commons.io.FileUtils.touch;
//
//public class AvcEncoder {
//
//    private MediaCodec mediaCodec;
//    private BufferedOutputStream outputStream;
//
//    public AvcEncoder() {
//        File f = new File(Environment.getExternalStorageDirectory(), "Download/video_encoded.264");
//        try {
//            touch(f);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            outputStream = new BufferedOutputStream(new FileOutputStream(f));
//            Log.i("AvcEncoder", "outputStream initialized");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            mediaCodec = MediaCodec.createEncoderByType("video/avc");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        MediaStore.Video.VideoColumns.WIDTH
//        MediaFormat mediaFormat = MediaFormat.createVideoFormat("video/avc", 320, 240);
//        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 125000);
//        mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 15);
////        mediaFormat.setInteger(MediaFormat.KEY_SAMPLE_RATE,44100 );
////        mediaFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
//        mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar);
//        mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 5);
//        mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
//        mediaCodec.start();
//    }
//
//    public void close() {
//        try {
//            mediaCodec.stop();
//            mediaCodec.release();
//            outputStream.flush();
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // called from Camera.setPreviewCallbackWithBuffer(...) in other class
//    public void offerEncoder(byte[] input) {
//        try {
//            ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers();
//            ByteBuffer[] outputBuffers = mediaCodec.getOutputBuffers();
//            int inputBufferIndex = mediaCodec.dequeueInputBuffer(-1);
//            if (inputBufferIndex >= 0) {
//                ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
//                inputBuffer.clear();
//                inputBuffer.put(input);
//                mediaCodec.queueInputBuffer(inputBufferIndex, 0, input.length, 0, 0);
//            }
//
//            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
//            int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
//            while (outputBufferIndex >= 0) {
//                ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];
//                byte[] outData = new byte[bufferInfo.size];
//                outputBuffer.get(outData);
//                outputStream.write(outData, 0, outData.length);
//                Log.i("AvcEncoder", outData.length + " bytes written");
//
//                mediaCodec.releaseOutputBuffer(outputBufferIndex, false);
//                outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
//
//            }
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
//
//    }
//}