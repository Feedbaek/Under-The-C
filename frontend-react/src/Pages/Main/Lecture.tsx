import { ILecture } from '../../Atoms/Lecture';

export const Lecture = (props: ILecture) => {
  return (
	<div className="max-w-md mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl m-6">
		<div className="md:flex">
			<div className="p-8">
				<div className="uppercase tracking-wide text-sm text-indigo-500 font-semibold">
					{props.lecture}
				</div>
					<a href="#" className="block mt-1 text-lg leading-tight font-medium text-black hover:underline">
						{props.title}
					</a>
					<p className="mt-2 text-slate-500">
						{props.content}
					</p>
			</div>
		</div>
	</div>
  );
};
